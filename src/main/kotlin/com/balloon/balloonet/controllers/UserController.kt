package com.balloon.balloonet.controllers

import com.balloon.balloonet.models.AuthRequest
import com.balloon.balloonet.models.User
import com.balloon.balloonet.repos.RoleRepo
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
    lateinit var roleRepository: RoleRepo

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
        user_id: Long
    ) {
        userRepository.deleteById(user_id)
        println("\nSUCCESS")
    }

    /**
     * Change level of a user
     */
    @PostMapping("/change")
    fun changeUser(
        @RequestParam(value = "role_id")
        role_id: Long, @RequestParam(value = "user_id")
        user_id: Long
    ) {
        val user = userRepository.findById(user_id).get()
        val role = roleRepository.findById(role_id).get()
        user.roles.add(role)
        userRepository.save(user)
    }

}