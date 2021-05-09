package com.balloon.balloonet.controllers

import com.balloon.balloonet.models.Role
import com.balloon.balloonet.repos.RoleRepo
import com.balloon.balloonet.repos.UserRepo
import com.balloon.balloonet.util.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("users")
class UserController {

    @Autowired
    lateinit var userRepository: UserRepo


    @Autowired
    lateinit var roleRepository: RoleRepo

    /**
     * Delete a user
     */
    @PostMapping("/delete")
    fun deleteUser(
        @RequestParam(value = "user_id")
        user_id: Long
    ): ResponseEntity<Any> {
        userRepository.deleteById(user_id)
        return ResponseEntity.ok().build()
    }

    /**
     * Change level of a user
     */
    @PostMapping("/change")
    fun changeUser(
        @RequestParam(value = "role_id")
        role_id: Long, @RequestParam(value = "user_id")
        user_id: Long
    ): ResponseEntity<Any> {
        val user = userRepository.findById(user_id).get()
        val role = roleRepository.findById(role_id).get()
        if (role in user.roles) {
            val hashSet = HashSet<Role>(user.roles)
            hashSet.remove(role)
            user.roles = hashSet
        } else {
            val hashSet = HashSet<Role>(user.roles)
            hashSet.add(role)
            user.roles = hashSet
        }
        userRepository.save(user)
        return ResponseEntity.ok().build()
    }

}