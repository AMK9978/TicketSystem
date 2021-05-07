package com.balloon.balloonet.controllers

import com.balloon.balloonet.dtos.UserDTO
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest


@RestController
class ProfileController {

    @GetMapping("/home")
    public fun home(): String {
        return "Hello"
    }


}