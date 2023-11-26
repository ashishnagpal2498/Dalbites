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

/**
 * Utility class for handling JSON Web Tokens (JWT).
 */
@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${jwt.secret}")
	private String secret;

	/**
	 * Extracts the username from a JWT token.
	 *
	 * @param token The JWT token.
	 * @return The username extracted from the token.
	 */
	public String getUsernameFromToken(String token) {
		Claims claims = getAllClaimsFromToken(token);
		return (String)claims.get("username");
	}

	/**
	 * Retrieves the expiration date from a JWT token.
	 *
	 * @param token The JWT token.
	 * @return The expiration date of the token.
	 */
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	/**
	 * Retrieves a specific claim from a JWT token using a resolver function.
	 *
	 * @param token          The JWT token.
	 * @param claimsResolver The resolver function to retrieve a specific claim.
	 * @param <T>            The type of the claim.
	 * @return The value of the requested claim.
	 */
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Retrieves all claims from a JWT token.
	 *
	 * @param token The JWT token.
	 * @return All claims from the token.
	 */
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * Checks if a JWT token is expired.
	 *
	 * @param token The JWT token.
	 * @return `true` if the token is expired, `false` otherwise.
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	/**
	 * Generates a JWT token with the specified claims and a one-month validity period.
	 *
	 * @param claims The claims to be included in the token.
	 * @return The generated JWT token.
	 */
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

	/**
	 * Generates a JWT token with the specified claims and a fifteen-minute validity period.
	 *
	 * @param claims The claims to be included in the token.
	 * @return The generated JWT token.
	 */
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

	/**
	 * Validates a JWT token against a UserDetails object.
	 *
	 * @param token        The JWT token.
	 * @param userDetails The UserDetails object.
	 * @return `true` if the token is valid, `false` otherwise.
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
