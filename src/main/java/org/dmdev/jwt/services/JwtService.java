package org.dmdev.jwt.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.dmdev.jwt.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${token.singing.key}")
    private String jwtSingInKey;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        if(userDetails instanceof User customUserDetails){
            claims.put("id", customUserDetails.getId());
            claims.put("email", customUserDetails.getEmail());
            claims.put("password", customUserDetails.getPassword());
        }
        return generateToken(claims, userDetails);
    }

    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolves){
        final Claims claims = extractAllClaims(token);
        return claimsResolves.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSinginKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSinginKey()).build().parseClaimsJwt(token)
                .getBody();
    }

    private Key getSinginKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSingInKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
