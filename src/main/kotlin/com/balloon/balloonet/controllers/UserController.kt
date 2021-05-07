package com.balloon.balloonet.controllers

import com.balloon.balloonet.util.Status
import com.balloon.balloonet.dtos.UserDTO
import com.balloon.balloonet.models.User
import com.balloon.balloonet.repos.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import javax.validation.Valid


@RestController
class UserController {

    @Autowired
    lateinit var userRepository: UserRepo


    @RequestMapping("/login")
    public fun login(
        @RequestParam(name = "email") email: @Valid String,
        @RequestParam(name = "password") password: @Valid String): String {
        val users = userRepository.findAll()
        return "login"
    }

    @GetMapping("/register")
    public fun showRegistrationForm(
        request: WebRequest,
        model: Model
    ): String {
        val userDTO = UserDTO()
        model.addAttribute("user", userDTO)
        return "registration"
    }

    @RequestMapping("/login-error")
    fun loginError(model: Model): String? {
        model.addAttribute("loginError", true)
        return "login.html"
    }


    @PostMapping("/users/register")
    fun registerUser(
        @RequestParam(name = "email") email: @Valid String,
        @RequestParam(name = "password") password: @Valid String,
        @RequestParam(name = "name", defaultValue = "A User") name: @Valid String,
        @RequestParam(name = "level", defaultValue = "0") level: @Valid Int
    ): Status? {
        val users: List<User> = userRepository.findAll()
        val newUser = User(email, password, name = name, level = level)
        println(newUser)
        if (newUser in users) {
            println("User Already exists!")
            return Status.USER_ALREADY_EXISTS
        }
        return try {
            userRepository.save(newUser)
            Status.SUCCESS
        } catch (exception: Exception) {
            Status.FAILURE
        }
    }

    /**
     * Delete a user
     */
    @PostMapping
    fun deleteUser(
        @RequestParam(value = "user_id")
        user_id: Int
    ) {

    }

    /**
     * Change level of a user
     */
    @PostMapping
    fun changeUser(
        @RequestParam(value = "level")
        level: Int, @RequestParam(value = "user_id")
        user_id: Int
    ) {

    }

}