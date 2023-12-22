package com.ejemplo.security.security.jwt;


import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class Util {
	
	
	private static final Logger logger = LoggerFactory.getLogger(Util.class);
	
	
	@Value("${jwt.secret.key}")
	private String secretKey;
	
	@Value ("{jwt.time.expiration}")
	private String timeExpiration;
	
	
	// 1 generar token de acceso
	public String generateTokenAccess(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ Long.parseLong(timeExpiration)))
				.signWith(getSignatureKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	// 2 Validar token acceso
	public boolean isTokenValid(String token) {
		
		logger.info("IsTokenValid: ".concat(token));
		
		try {
			Jwts.parserBuilder().setSigningKey(getSignatureKey())
			.build()
			.parseClaimsJws(token)
			.getBody();
			return true;
		}catch(Exception e) {
			logger.error("Token invalido, error ".concat(e.getMessage()));
			return false;
		}	
				
	}
	
	
	//3 Obtener el username del token
	public String getUsernameFromToken(String token) {
		return getClaim(token, Claims::getSubject);
	}
	
	
	//4 Obtener un solo claim
	public <T> T getClaim( String token, Function<Claims, T> claimsTFunction) {
		Claims claim = extractAllClaims(token);
		return claimsTFunction.apply(claim);
	}
	
	
	
	//5 Obtener todos los claims  del token
	public Claims extractAllClaims(String token) {
		
		return 
				Jwts.parserBuilder()
				.setSigningKey(getSignatureKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	
	}
	
		
	//6 Obtener firma del token
	public Key getSignatureKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
