package com.balloon.balloonet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class BalloonetApplication

fun main(args: Array<String>) {
	runApplication<BalloonetApplication>(*args)
}

@Bean fun bCryptPasswordEncoder(): BCryptPasswordEncoder{
	return BCryptPasswordEncoder()
}
