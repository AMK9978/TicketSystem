package com.balloon.balloonet.controllers

import com.balloon.balloonet.models.AuthRequest
import com.balloon.balloonet.models.User
import com.balloon.balloonet.repos.UserRepo
import com.balloon.balloonet.util.JwtUtil
import com.balloon.balloonet.util.Status
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
class UserController {

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
                UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
            )
        } catch (ex: java.lang.Exception) {
            throw java.lang.Exception("inavalid username/password")
        }
        return jwtUtil.generateToken(authRequest.username)
    }

    @RequestMapping("/login-error")
    fun loginError(model: Model): String? {
        model.addAttribute("loginError", true)
        return "login.html"
    }


    @PostMapping("/signup")
    fun registerUser(
        @RequestBody user: User
    ): Status? {
        val users: List<User> = userRepository.findAll()
        println(user)
        if (user in users) {
            println("User Already exists!")
            return Status.USER_ALREADY_EXISTS
        }
        return try {
            userRepository.save(user)
            Status.SUCCESS
        } catch (exception: Exception) {
            Status.FAILURE
        }
    }

    /**
     * Delete a user
     */
    @PostMapping("/delete")
    fun deleteUser(
        @RequestParam(value = "user_id")
        user_id: Int
    ) {

    }

    /**
     * Change level of a user
     */
    @PostMapping("/change")
    fun changeUser(
        @RequestParam(value = "level")
        level: Int, @RequestParam(value = "user_id")
        user_id: Int
    ) {

    }

}