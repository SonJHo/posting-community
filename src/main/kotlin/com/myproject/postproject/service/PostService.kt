package com.myproject.postproject.service

import com.myproject.postproject.domain.Board
import com.myproject.postproject.domain.Post
import com.myproject.postproject.repository.BoardRepository
import com.myproject.postproject.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
@Transactional(readOnly = true)
class PostService(
    private val postRepository: PostRepository,
    private val boardRepository: BoardRepository,
) {

    fun createPost(){

    }

    @Transactional
    fun postUp(post: Post) {
        post.date = LocalDateTime.now()
        postRepository.save(post)
        post.board!!.postList.add(post)
    }


    @Transactional
    fun postDown(post: Post) {
        post.board!!.postList.remove(post)
        post.board = null
        postRepository.remove(post)

    }


}