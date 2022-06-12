package com.example.jpatest.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
public class AES256Provider {

    private final String algorithm;
    private final String secretKey;
    private final String iv;

    public AES256Provider(
            @Value("${security.common.algorithm}") String algorithm
            ,@Value("${security.common.secret-key}") String secretKey) {
        this.algorithm = algorithm;
        this.secretKey = secretKey;
        // TODO 1
        //Contants 라는 클래스파일 만들어서 스테틱필드로 상수 다 지정해놨는데 여기에는 왜 yaml이 아닌 클래스에 스테틱필드로 상수로 해놨는지 모르겠..
        this.iv = this.secretKey.substring(0, 16);
    }

    public String encrypt(String text) throws Exception{

        Cipher cipher = Cipher.getInstance(this.algorithm);

        SecretKeySpec keySpec = new SecretKeySpec(this.secretKey.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(this.iv.getBytes());

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);

    }

    public String encrypt(String text, String key) throws Exception {

        // 암호화 알고리즘 설정
        Cipher cipher = Cipher.getInstance(this.algorithm);

        // 암호화에 사용되는 키
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(key.substring(0,16).getBytes());

        // 암호화 객체에 키 설정
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

        // 암호화 문자열 생성
        byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * Parameter 로 받은 문자열을 복호화 하는 메소드
     * @param cipherText  복호화 대상이 되는 문자열
     * @return 복호화된 문자열
     */
    public String decrypt(String cipherText) throws Exception {

        // 복호화 알고리즘 설정
        Cipher cipher = Cipher.getInstance(this.algorithm);

        // 복호화에 사용되는 키
        SecretKeySpec keySpec = new SecretKeySpec(this.secretKey.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(this.iv.getBytes());

        // 복호화 객체에 키 설정
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        // 복호화 문자열 생성
        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * Parameter 로 받은 문자열을 키로 복호화 하는 메소드
     * @param cipherText  복호화 대상이 되는 문자열
     * @param key   복호화 하기 위한 키
     * @return 복호화된 문자열
     */
    public String decrypt(String cipherText, String key) throws Exception {

        // 복호화 알고리즘 설정
        Cipher cipher = Cipher.getInstance(this.algorithm);

        // 복호화에 사용되는 키
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParamSpec = new IvParameterSpec(key.substring(0,16).getBytes());

        // 복호화 객체에 키 설정
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

        // 복호화 문자열 생성
        byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
        byte[] decrypted = cipher.doFinal(decodedBytes);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * AES 에 사용되는 키를 생성하는 메소드
     * @return AES 에 사용할 unique 키
     */
    public String generateAESKey(){
        // 개인 키 생성
        String uuid = UUID.randomUUID().toString();

        // 32 자리 키 생성
        return uuid.substring(0, 32);
    }
}
