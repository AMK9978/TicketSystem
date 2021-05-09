package com.balloon.balloonet.controllers

import com.balloon.balloonet.models.AuthRequest
import com.balloon.balloonet.models.User
import com.balloon.balloonet.repos.UserRepo
import com.balloon.balloonet.util.JwtUtil
import com.balloon.balloonet.util.getMessageBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("auth")
class AuthController {
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userRepository: UserRepo

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtUtil: JwtUtil

    @PostMapping("/login")
    @Throws(java.lang.Exception::class)
    fun generateToken(@RequestBody authRequest: AuthRequest): ResponseEntity<String> {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(authRequest.email, authRequest.password)
            )
        } catch (ex: java.lang.Exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getMessageBody(value = "Invalid email/password"))
        }
        return ResponseEntity.ok(jwtUtil.generateToken(authRequest.email)?.let { getMessageBody("token", it) })
    }

    @PostMapping("/signup")
    fun registerUser(
        @RequestBody user: User
    ): ResponseEntity<Any> {
        if (userRepository.existsByEmail(user.email)) {
            return ResponseEntity.badRequest().body(getMessageBody(value = "User Already exists!"))
        }
        return try {
            user.password = passwordEncoder.encode(user.password)
            userRepository.save(user)
            ResponseEntity.ok().body(getMessageBody(value = "User registered"))
        } catch (exception: Exception) {
            ResponseEntity.badRequest().build()
        }
    }
}