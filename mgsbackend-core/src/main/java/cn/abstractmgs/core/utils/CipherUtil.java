package cn.abstractmgs.core.utils;

import io.cucumber.java.an.E;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

@Slf4j
public class CipherUtil {

    public static String decryptPhoneNumber(String sessionKey, String encryptedData, String iv){
        byte[] encData = Base64Utils.decodeFromString(encryptedData);
        byte[] ivb = Base64Utils.decodeFromString(iv);
        byte[] key = Base64Utils.decodeFromString(sessionKey);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivb);
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return new String(cipher.doFinal(encData), StandardCharsets.UTF_8);
        } catch (Exception e){
            log.error("decryptPhoneNumber: ", e);
        }
        return null;
    }
}
