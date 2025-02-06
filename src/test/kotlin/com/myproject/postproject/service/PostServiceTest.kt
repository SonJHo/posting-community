package com.myproject.postproject.service

import com.myproject.postproject.domain.Board
import com.myproject.postproject.domain.Post
import com.myproject.postproject.repository.BoardRepository
import com.myproject.postproject.repository.PostRepository
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
class PostServiceTest @Autowired constructor(
    private val em : EntityManager,
    private val postService: PostService,
    private val postRepository: PostRepository,
    private val boardRepository: BoardRepository
){


    @Test
    fun 게시글_저장(){
    //given
        val board = boardRepository.createBoard("웹프로그래밍 강의 게시판", "손준호")

        val post = Post()
        post.title = "공지사항"
        post.board = board
        post.content ="이번주 강의는 휴강입니다"

        //when
        boardRepository.save(board)
        postService.postUp(post)

        em.flush()
    //then
        val posts = postRepository.findAll()
        val findBoard = boardRepository.findOne(board.id!!)

        Assertions.assertThat(posts!!.size).isSameAs(1)
        Assertions.assertThat(findBoard!!.postList.size).isSameAs(1)
    }


    @Test
    fun 게시글_삭제(){
        //given
        val board = boardRepository.createBoard("웹프로그래밍 강의 게시판", "손준호")

        val post = Post()
        post.title = "공지사항"
        post.board = board
        post.content ="이번주 강의는 휴강입니다"

        //when
        boardRepository.save(board)
        postService.postUp(post)

        em.flush()
        postService.postDown(post)
        //then
        val posts = postRepository.findAll()

        Assertions.assertThat(posts!!.size).isSameAs(0)
        Assertions.assertThat(board.postList.size).isSameAs(0)
    }




}