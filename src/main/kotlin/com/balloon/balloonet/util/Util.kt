package com.balloon.balloonet.util

import com.balloon.balloonet.models.User
import com.balloon.balloonet.repos.RoleRepo

const val USER = "USER"
const val SUPPORTER = "SUPPORTER"
const val ADMIN = "ADMIN"

var userLevel: HashMap<String, Int> = hashMapOf(USER to 0, SUPPORTER to 1, ADMIN to 2)


fun isAdminOrSupporter(user: User, roleRepo: RoleRepo): Boolean {
    return isAdmin(user, roleRepo) || user.roles.map { role -> role.name }.contains(roleRepo.findByName(SUPPORTER).name)
}

fun isAdmin(user: User, roleRepo: RoleRepo): Boolean {
    return user.roles.map { role -> role.name }.contains(roleRepo.findByName(ADMIN).name)
}