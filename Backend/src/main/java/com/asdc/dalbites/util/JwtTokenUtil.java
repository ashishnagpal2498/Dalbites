package com.asdc.dalbites.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {

	@Value("${jwt.secret}")
	private String secret;

	public String getUsernameFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return (String)claims.get("username");
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(Map<String, Object> claims) {
		LocalDate localDate = LocalDate.now();
    	localDate = localDate.plusMonths(1);
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date())
			.setExpiration(date)
			.signWith(SignatureAlgorithm.HS256, secret)
			.compact();
	}

	public String generateFifteenMinuteExpiryToken(Map<String, Object> claims) {
		LocalDateTime localDate = LocalDateTime.now();
    	localDate = localDate.plusMinutes(Constants.TEMP_TOKEN_EXPIRY_TIME);
		Date date = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date())
			.setExpiration(date)
			.signWith(SignatureAlgorithm.HS256, secret)
			.compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
