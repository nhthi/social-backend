package vn.nht.social.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {
    private static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
    public static String generateToken(Authentication auth) {
        String jwt = Jwts.builder()
                .setIssuer("CodewithThinh")
                .setIssuedAt(new Date())
                .setExpiration(new Date( new Date().getTime() + 86400000))
                .claim("email",auth.getName())
                .signWith(key).compact();
        return jwt;
    }

    public static String getEmailFromJwtToken(String jwt) throws MalformedJwtException {
        // Kiểm tra xem token có bắt đầu bằng "Bearer " hay không
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        } else {
            throw new MalformedJwtException("Invalid token format");
        }

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
            return String.valueOf(claims.get("email"));
        } catch (MalformedJwtException e) {
            // Xử lý trường hợp token không hợp lệ
            throw new MalformedJwtException("Invalid token");
        }
    }
}
