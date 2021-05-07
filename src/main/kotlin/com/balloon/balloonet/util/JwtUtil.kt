package com.balloon.balloonet.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function


@Service
class JwtUtil {

    private val secret = "OsYmq54YdOZF+Ipb8YVoIMT13EruDvOW1nHzAbR3C2zO4K4sVVifwE77f4oCtVfJX6Xa+kOoMCGAJdNd7SDWYQ=="


    fun extractUsername(token: String?): String? {
        return extractClaim(token) { obj: Claims? -> obj!!.subject }
    }

    fun extractExpiration(token: String?): Date? {
        return extractClaim(token) { obj: Claims? -> obj!!.expiration }
    }


    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims?, T>): T {
        val claims: Claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody()
    }

    private fun isTokenExpired(token: String?): Boolean? {
        return extractExpiration(token)?.before(Date())
    }

    fun generateToken(username: String): String? {
        val claims: Map<String, Any> = HashMap()
        return createToken(claims, username)
    }

    private fun createToken(claims: Map<String, Any>, subject: String): String? {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS256, secret).compact()
    }

    fun validateToken(token: String?, userDetails: UserDetails): Boolean? {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)!!
    }
}