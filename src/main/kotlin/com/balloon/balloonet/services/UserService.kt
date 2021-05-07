package com.balloon.balloonet.services

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService: UserDetailsService{
    override fun loadUserByUsername(username: String?): UserDetails {
        val user =
    }

}