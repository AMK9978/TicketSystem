package com.balloon.balloonet.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ProfileController {

    @GetMapping("/home")
    public fun home(): String {
        return "Hello"
    }


}