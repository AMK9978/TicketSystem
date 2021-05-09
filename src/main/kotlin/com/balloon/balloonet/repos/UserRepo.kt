package com.balloon.balloonet.repos

import com.balloon.balloonet.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepo : JpaRepository<User, Long> {
    fun findByEmail(email: String?): User
    fun existsByEmail(email: String?): Boolean
}