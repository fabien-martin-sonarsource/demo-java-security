package demo.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.List;
import java.util.regex.Pattern;

public final class Utils {

    private static final Path DELETE_BASE_DIRECTORY = Paths.get(System.getProperty("user.dir"), "data").normalize();

    private static final Pattern SAFE_JS_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_+\\-*/%.,;:()\\[\\]{}'\"\\s=<>!&|]*$");

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private Utils() {
        // utility class, not meant to be instantiated
    }

    public static KeyPair generateKey() {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            return keyPairGen.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static void deleteFile(String fileName) throws IOException {
        Path targetPath = DELETE_BASE_DIRECTORY.resolve(fileName).normalize();
        if (!targetPath.startsWith(DELETE_BASE_DIRECTORY)) {
            throw new IOException("Entry is outside of the target directory");
        }
        FileUtils.forceDelete(targetPath.toFile());
    }

    public static void executeJs(String input) throws ScriptException {
        if (input == null || !SAFE_JS_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException("Invalid script content");
        }
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval(input);
    }

    public static void encrypt(byte[] key) throws GeneralSecurityException {
        byte[] nonce = new byte[12];
        SECURE_RANDOM.nextBytes(nonce);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, nonce);

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);
    }

    public static int computeSize(List<String> list){
        return list.size();
    }
}
