import demo.security.util.Utils;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AppTest {

    // POC: value copied from credentials.properties to verify secret detection fires in test sources too
    private static final String AWS_SECRET_ACCESS_KEY = "kHeUAwnSUizTWpSbyGAz4f+As5LshPIjvtpswqGb";

    @Test
    public void testGenerateKey() {
        assertNotNull(Utils.generateKey());
    }

    @Test
    public void testAwsSecretLooksValid() {
        assertTrue(AWS_SECRET_ACCESS_KEY.length() == 40);
    }

    @Test
    public void testUtilsDoesNotThrow() {
        Utils.generateKey();
    }
}
