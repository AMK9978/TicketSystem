package com.balloon.balloonet.repos

import com.balloon.balloonet.models.Role
import com.balloon.balloonet.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepo : JpaRepository<Role, Long>{
    fun findByName(name: String): Role
}