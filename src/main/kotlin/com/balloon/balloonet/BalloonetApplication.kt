package com.balloon.balloonet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BalloonetApplication

fun main(args: Array<String>) {
    runApplication<BalloonetApplication>(*args)
}