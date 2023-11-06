package com.project.questApp.security;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${questapp.app.secret}")//application.properties dosyasından değer çekeceğimiz için böyle yaptık
    private String APP_SECRET; //Token'lar buna göre oluşturulur
    @Value("${questapp.expires.in}")
    private long EXPIRES_IN; //Tokenlerin geçerliliğini yitirme süresi

    public String generateJwtToken(Authentication auth){
        JwtUserDetails userDetails=(JwtUserDetails) auth.getPrincipal();
        Date expiresDate=new Date(new Date().getTime()+EXPIRES_IN);
        //Key Oluşturma işlemi
        return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date()).setExpiration(expiresDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET).compact();
    }

    Long getUserIdFromJwt(String token){
        Claims claims=parse(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    boolean validateToken(String token){
        try {
            parse(token);
            return !isTokenExpired(token);
        }catch (SignatureException e){
            return false;
        }catch (MalformedJwtException e){
            return false;
        }catch (ExpiredJwtException e){
            return false;
        }catch (UnsupportedJwtException e){
            return false;
        }catch (IllegalArgumentException e){
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expration= parse(token).getBody().getExpiration();
        return expration.before(new Date());
    }
    Jwt<Header, Claims> parse(String token){
        return Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJwt(token);
    }

}
