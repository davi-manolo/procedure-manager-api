package com.procedure.manager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataEncoderUtils {

    public static String encodeContent(byte[] contentByte) {
        byte[] encryptedByte = Base64.getEncoder().encode(contentByte);
        return new String(encryptedByte, StandardCharsets.UTF_8);
    }

    public static String encodePassword(String password) {
        return getPasswordEncoder().encode(password);
    }

    public static BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
