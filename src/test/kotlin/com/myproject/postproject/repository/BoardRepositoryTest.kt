package com.myproject.postproject.repository

import com.myproject.postproject.domain.Board
import com.myproject.postproject.domain.Member
import com.myproject.postproject.service.MemberService
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@SpringBootTest
@Transactional
class BoardRepositoryTest @Autowired constructor(
    private val em :EntityManager,
    private val boardRepository: BoardRepository,
    private val memberService: MemberService
){


    fun createNewMember(
        accountId: String = "wnsgh708282",
        password: String = "wnsgh1598",
        name: String = "손준호",
    ): Member {
        return Member(accountId = accountId, password = password, name = name)
    }
    @Test
    fun 게시판_생성 (){
    //given

        val board = Board()
        board.name = "자료구조게시판"
        board.createBy = "손준호"
        board.date = LocalDateTime.now()
        //when
        boardRepository.save(board)
        em.flush()
    //then
        Assertions.assertThat(board).isEqualTo(boardRepository.findById(board.id!!))

    }

    @Test
    fun 게시판_삭제 (){
        //given
        val board = Board()
        board.name = "자료구조게시판"
        board.createBy = "손준호"
        board.date = LocalDateTime.now()
        //when
        boardRepository.save(board)
        em.flush()

        boardRepository.remove(board)
        em.flush()

        //then
        Assertions.assertThat(boardRepository.findAll()!!.size).isSameAs(0)
    }

    @Test
    fun 게시판_전체조회 (){
        //given
        val board1 = Board()
        board1.name = "자료구조게시판"
        board1.createBy = "손준호"
        board1.date = LocalDateTime.now()

        val board2 = Board()
        board2.name = "인공지능게시판"
        board2.createBy = "진수연"
        board2.date = LocalDateTime.now()
        //when
        boardRepository.save(board1)
        boardRepository.save(board2)
        em.flush()
        //then
        val boards = boardRepository.findAll()
        Assertions.assertThat(boards!!.size).isSameAs(2)
    }

}