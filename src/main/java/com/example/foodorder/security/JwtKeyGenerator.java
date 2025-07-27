package com.example.foodorder.security;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
public class JwtKeyGenerator {
    public static void main(String[] args) {
        String key = Base64.getEncoder().encodeToString(
                Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded()
        );
        System.out.println("Generated JWT secret key (base64): " + key);
    }
}
