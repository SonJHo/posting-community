package com.myproject.postproject.service

import com.myproject.postproject.domain.Comment
import com.myproject.postproject.domain.Post
import com.myproject.postproject.repository.CommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
@Transactional(readOnly = true)
class CommentService(
    private val commentRepository: CommentRepository,
) {

    fun createComment(){

    }

    @Transactional
    fun commentUp(comment: Comment) {
        comment.date = LocalDateTime.now()
        commentRepository.save(comment)
        comment.post!!.comments.add(comment)
    }



    @Transactional
    fun commentDown(comment: Comment) {
        comment.post!!.comments.remove(comment)
        comment.post = null
        commentRepository.remove(comment)
    }
}