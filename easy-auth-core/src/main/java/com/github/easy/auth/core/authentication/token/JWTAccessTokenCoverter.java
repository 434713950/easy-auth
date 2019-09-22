package com.github.easy.auth.core.authentication.token;


import com.github.easy.auth.core.AuthConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import sun.misc.BASE64Decoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * jwt 授权令牌处理器
 *
 * @author cheng.peng
 * @date 2019/8/28
 */
public class JWTAccessTokenCoverter implements AccessTokenCoverter {

    private String secretKey;

    private BASE64Decoder decoder = new BASE64Decoder();

    public JWTAccessTokenCoverter(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String generateToken(AccessToken accessToken) {
        SecretKey key = generalKey(secretKey);
        JwtBuilder builder = Jwts.builder()
                .setId(String.valueOf(UUID.randomUUID()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(accessToken.getSub())
                .signWith(SignatureAlgorithm.HS256, key)
                .setExpiration(accessToken.getExpiration());
        Date expireDate = accessToken.getExpiration();
        if (expireDate != null) {
            builder.setExpiration(expireDate);
        }
        return builder.compact();
    }

    @Override
    public AccessToken parse(String token) {
        Claims claims = Jwts
                        .parser()
                        .setSigningKey(generalKey(AuthConstants.JWT_SECRETKEY))
                        .parseClaimsJws(token)
                        .getBody();
        AccessToken accessToken = new AccessToken();
        accessToken.setSub(claims.getSubject());
        accessToken.setExpiration(claims.getExpiration());
        return accessToken;
    }


    private SecretKey generalKey(String secretKey) {
        byte[] encodedKey = null;
        try {
            decoder.decodeBuffer(secretKey);
        } catch (IOException e) {
            //nothing;
            encodedKey = secretKey.getBytes();
        }
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }
}
