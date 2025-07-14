package vn.flast.jwt;

import org.springframework.security.core.GrantedAuthority;
import vn.flast.security.UserPrinciple;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private final String jwtSecret = "jwtEKids@2019#nchn";

    public String generateJwtToken(UserPrinciple userPrincipal) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("ssoId", userPrincipal.getSsoId());
        claims.put("userId", String.valueOf( userPrincipal.getId()) );
        List<String> roles = userPrincipal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
        claims.put("roles", roles);
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        /* 48 * 3600000 ((48 Giờ)); */
        long jwtExpiration = 172800000;
        long expMillis = nowMillis + jwtExpiration;
        Date exp = new Date(expMillis);
        
        return Jwts.builder()
            .setId(Integer.toString(userPrincipal.getId()))
            .setIssuedAt(now)
            .setSubject((userPrincipal.getUsername()))
            .setExpiration(exp)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .addClaims(claims)
            .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e.getMessage());
        }
        return false;
    }
    
    public Claims getClaims(String token) {
        return Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody();
    }
}
