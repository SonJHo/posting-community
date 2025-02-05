package com.myproject.postproject

import com.myproject.postproject.domain.Member
import com.myproject.postproject.repository.MemberRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PostprojectApplication

fun main(args: Array<String>) {
	runApplication<PostprojectApplication>(*args)

}
