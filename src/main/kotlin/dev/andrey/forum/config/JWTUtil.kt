package dev.andrey.forum.config

import dev.andrey.forum.service.UsuarioService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JWTUtil(
    @Value("\${jwt.secret}")
    private val secret: String,
    private val usuarioService: UsuarioService
) {

    private val expiration: Long = 60000

    fun generateToken(username: String, authorities: Collection<String> = emptyList()): String? {
        val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
        return Jwts.builder()
            .subject(username)
            .claim("role", authorities)
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(key)
            .compact()
    }

    fun isValidToken(token: String): Boolean {
        return try {
            val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAuthentication(jwt: String): Authentication {
        val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())
        val username = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).payload.subject
        val user = usuarioService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, null, user.authorities)
    }
}