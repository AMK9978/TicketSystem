package com.balloon.balloonet.services

import com.balloon.balloonet.bCryptPasswordEncoder
import com.balloon.balloonet.models.MyUserDetails
import com.balloon.balloonet.models.Role
import com.balloon.balloonet.models.User
import com.balloon.balloonet.repos.RoleRepo
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

    @Autowired
    lateinit var roleRepository: RoleRepo

    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userRepository.findByEmail(username)
        return MyUserDetails(user)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun create(user: User, role: Role) {
        user.password = bCryptPasswordEncoder().encode(user.password)
        user.roles.add(role)
        userRepository.save(user)
    }
}