package com.balloon.balloonet.util

const val USER = "USER"
const val SUPPORTER = "SUPPORTER"
const val ADMIN = "ADMIN"

var userLevel: HashMap<String, Int> = hashMapOf(USER to 0, SUPPORTER to 1, ADMIN to 2)