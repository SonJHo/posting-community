package com.myproject.postproject.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun main() {

    val encoder = BCryptPasswordEncoder()
    val rawPassword = "1234"
    val encodedPassword = encoder.encode(rawPassword)

    println("encodedPassword = $encodedPassword")
}