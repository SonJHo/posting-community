package com.myproject.postproject.repository

import com.myproject.postproject.domain.Grade
import com.myproject.postproject.domain.Member
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
class MemberRepositoryTest(
    private val em : EntityManager,
    private val memberRepository: MemberRepository
){
    fun createNewMember(
        accountId: String,
        password: String,
        name: String,
        studentId: Long
    ): Member {
        return Member(accountId = accountId, password = password, name = name, studentId = studentId)
    }


}