package cn.meteor.im.util;

import cn.meteor.im.dto.JwtResult;
import cn.meteor.im.entity.LocalAuth;
import cn.meteor.im.entity.UserInfo;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @author Meteor
 */
public class JwtUtil {

    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public static String secretKey = "oakUsdhNSOjodasasd";

    /**
     * 生成token
     * @param claims
     * @param ttl
     * @return
     */
    public static JwtResult createJwt(Claims claims, long ttl) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setClaims(claims)
                .setIssuedAt(now);

        builder.signWith(signatureAlgorithm, signingKey);

        long expMillis = 0L;
        if (ttl >= 0) {
            expMillis = nowMillis + ttl * 1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        String token = builder.compact();
        return new JwtResult(token, expMillis);
    }

    public JwtResult updateToken(String token, long ttl) {
        try {
            Claims claims = verifyToken(token);
            return createJwt(claims, ttl);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }





    public static Claims verifyToken(String token) {
        Claims claims = claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .parseClaimsJws(token).getBody();

        return claims;
    }

}
