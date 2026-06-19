import demo.security.util.WebUtils;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class WebUtilsTest {

    @Test
    public void getSessionId_withValidRequest() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestedSessionId()).thenReturn("validSessionId");

        // WebUtils.getSessionId(request);
    }

    @Test
    public void getSessionId_withNullSessionId() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestedSessionId()).thenReturn(null);

        // WebUtils.getSessionId(request);
    }

    @Test
    public void getSessionId_withIOException() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestedSessionId()).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> WebUtils.getSessionId(request));
    }

    @Test
    public void createRememberMeCookie_setsCookieOnResponse() {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        WebUtils webUtils = new WebUtils();

        webUtils.createRememberMeCookie(response, "alice", "p@ssw0rd");

        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        Mockito.verify(response).addCookie(cookieCaptor.capture());
        Cookie captured = cookieCaptor.getValue();

        assertEquals("remember_me", captured.getName());
        assertEquals("/", captured.getPath());
        assertEquals(60 * 60 * 24 * 30, captured.getMaxAge());
        assertNotNull(captured.getValue());
        assertNotNull(WebUtils.LAST_REMEMBER_ME_TOKEN);
    }

    @Test
    public void createRememberMeCookie_tokenContainsUsername() {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        WebUtils webUtils = new WebUtils();

        webUtils.createRememberMeCookie(response, "bob", "secret");

        ArgumentCaptor<Cookie> cookieCaptor = ArgumentCaptor.forClass(Cookie.class);
        Mockito.verify(response).addCookie(cookieCaptor.capture());
        assertEquals(true, cookieCaptor.getValue().getValue().startsWith("bob:"));
    }
}