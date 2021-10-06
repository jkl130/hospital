package com.anjsh.utils;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * jwt工具类
 *
 * @author anjsh
 * @date 2020/11/18 15:54
 */
public class JwtTokenUtils {

    private JwtTokenUtils() {
    }

    /**
     * 令牌头名字
     */
    public static final String AUTHORIZE_TOKEN = "Authorization";

    public static final String ADMIN = "admin";

    //秘钥
    /**
     * 用户
     */
    private static final String SECRET_USER = "5pil6aOO5YaN576O5Lmf5q+U5LiN5LiK5bCP6ZuF55qE56yR";

    /**
     * 管理员
     */
    private static final String SECRET_ADMIN = "ADMIN5pil6aOO5YaN576O5Lmf5q+U5LiN5LiK5bCP6ZuF55qE56yR";

    private static final String BEARER = "Bearer ";

    /***
     * 生成令牌-管理员
     * @param uid:唯一标识符
     * @param ttlMillis:有效期
     */
    public static String generateTokenAdmin(String uid, Map<String, Object> payload, long ttlMillis) {
        return generateToken(uid, payload, ttlMillis, SECRET_ADMIN);
    }

    /***
     * 生成令牌-普通用户
     * @param uid:唯一标识符
     * @param ttlMillis:有效期
     */
    public static String generateTokenUser(String uid, Map<String, Object> payload, long ttlMillis) {
        return generateToken(uid, payload, ttlMillis, SECRET_USER);
    }

    /***
     * 生成令牌
     * @param uid:唯一标识符
     * @param ttlMillis:有效期
     */
    private static String generateToken(String uid, Map<String, Object> payload, long ttlMillis, String secret) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Key signingKey = new SecretKeySpec(secret.getBytes(), signatureAlgorithm.getJcaName());

        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        JwtBuilder builder = Jwts.builder().setId(uid)
                .setIssuedAt(now)
                .setIssuer(uid)
                .setSubject(uid)
                .setHeader(header)
                .signWith(signatureAlgorithm, signingKey);

        //设置载体
        builder.addClaims(payload);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static Map<String, Object> parseToken(String token) {
        try {
            return parseToken(token, SECRET_USER);
        } catch (Exception e) {
            Map<String, Object> payload = parseToken(token, SECRET_ADMIN);
            payload.put(ADMIN, true);
            return payload;
        }
    }


    /***
     * 解密JWT令牌
     */
    private static Map<String, Object> parseToken(String token, String secret) {
        //以Bearer开头处理
        if (token.startsWith(BEARER)) {
            token = token.substring(7).trim();
        }

        //秘钥处理
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Key signingKey = new SecretKeySpec(secret.getBytes(), signatureAlgorithm.getJcaName());

        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }
}



