package com.myproject.postproject.repository

import com.myproject.postproject.domain.Board
import com.myproject.postproject.domain.Comment
import com.myproject.postproject.domain.Member
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@SpringBootTest
@Transactional
class CommentRepositoryTest @Autowired constructor(
    private val em : EntityManager,
    private val commentRepository: CommentRepository
)
{

    fun createNewMember(
        accountId: String,
        password: String,
        name: String,
    ): Member {
        return Member(accountId = accountId, password = password, name = name)
    }


}