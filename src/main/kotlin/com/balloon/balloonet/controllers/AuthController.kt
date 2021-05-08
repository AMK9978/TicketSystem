package com.balloon.balloonet.controllers

import com.balloon.balloonet.models.AuthRequest
import com.balloon.balloonet.models.User
import com.balloon.balloonet.repos.UserRepo
import com.balloon.balloonet.util.JwtUtil
import com.balloon.balloonet.util.Status
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("auth")
class AuthController {

    @Autowired
    lateinit var userRepository: UserRepo

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtUtil: JwtUtil

    @PostMapping("/login")
    @Throws(java.lang.Exception::class)
    fun generateToken(@RequestBody authRequest: AuthRequest): String? {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(authRequest.email, authRequest.password)
            )
        } catch (ex: java.lang.Exception) {
            throw java.lang.Exception("inavalid username/password")
        }
        return jwtUtil.generateToken(authRequest.email)
    }

    @RequestMapping("/login-error")
    fun loginError(model: Model): String? {
        model.addAttribute("loginError", true)
        return "login.html"
    }


    @PostMapping("/signup")
    fun registerUser(
        @RequestBody user: User
    ): ResponseEntity<Any> {
        val users: List<User> = userRepository.findAll()
        println(user)
        if (user in users) {
            return ResponseEntity.badRequest().body("User Already exists!")
        }
        return try {
            userRepository.save(user)
            ResponseEntity.ok().body("User registered")
        } catch (exception: Exception) {
            ResponseEntity.badRequest().build()
        }
    }
}