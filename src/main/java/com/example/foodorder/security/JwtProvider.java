//package com.example.foodorder.security;
//
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import java.util.Date;
//
//@Component
//public class JwtProvider {
//    @Value("${jwt.secret}")
//    private String jwtSecret;
//    @Value("${jwt.expiration}")
//    private Long jwtExpiration;
//
//    public String generateToken(String username){
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime() + jwtExpiration))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//
//    public String getUsernameFromToken(String token){
//    return Jwts.parser()
//            .setSigningKey(jwtSecret)
//            .parseClaimsJws(token)
//            .getBody()
//            .getSubject();
//
//    }
//
//    public boolean validateToken(String token){
//        try {
//        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
//        return true;
//        }catch (JwtException | IllegalArgumentException e){
//        e.printStackTrace();
//        }
//        return false;
//    }
//
//}
