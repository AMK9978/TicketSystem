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


@RestController
@RequestMapping("users")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepo

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtUtil: JwtUtil

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