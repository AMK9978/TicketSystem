package com.balloon.balloonet.services

import com.balloon.balloonet.models.MyUserDetails
import com.balloon.balloonet.repos.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService : UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepo

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByEmail(username)
        return MyUserDetails(user)
    }
}