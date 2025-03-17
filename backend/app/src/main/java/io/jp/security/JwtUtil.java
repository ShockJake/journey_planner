package io.jp.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import org.springframework.stereotype.Component;
import java.util.Date;


import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private static final SecretKey SECRET_KEY = SIG.HS256.key().build();
    private final JwtParser parser = Jwts.parser()
            .decryptWith(SECRET_KEY)
            .build();

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return parser.parseUnsecuredClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username);
    }
}
