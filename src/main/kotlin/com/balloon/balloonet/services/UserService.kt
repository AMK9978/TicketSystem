package com.balloon.balloonet.services

import com.balloon.balloonet.bCryptPasswordEncoder
import com.balloon.balloonet.models.User
import com.balloon.balloonet.repos.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService : UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepo

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByEmail(username)
        return org.springframework.security.core.userdetails
            .User(user.email,user.password, arrayListOf())
    }

    @Transactional(rollbackFor = [Exception::class])
    fun saveDto(userDto: User) {
        userDto.password = bCryptPasswordEncoder()
                .encode(userDto.password)

        userRepository.save(userDto)
    }
}