package com.kakaopay.support.jwt.service;

import com.kakaopay.support.jwt.model.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtService {
    private static final String SECRET_KEY = "KakaoPayBestPinTechCompanyInWorld";

    public String generateJwtToken(User user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + 1000 * 60 * 1);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey =  new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("typ","JWT");
        headerMap.put("alg","HS256");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", user.getId());
        map.put("passwd", user.getPasswd());

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expireTime)
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    public boolean isUseableJwtToken(String jwt) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(jwt).getBody(); // 정상 수행된다면 해당 토큰은 정상토큰

            log.info("expireTime :" + claims.getExpiration());
            log.info("id :" + claims.get("id"));
            log.info("passwd :" + claims.get("passwd"));

            return true;
        } catch (ExpiredJwtException exception) {
            log.info("토큰 만료");
            return false;
        } catch (JwtException exception) {
            log.info("토큰 변조");
            return false;
        }
    }

    public String refreshJwtToken(String jwt) throws Exception {
        if (isUseableJwtToken(jwt)) {
            User user = new User();
            return generateJwtToken(user);
        }
        return null;
    }
}
