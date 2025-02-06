package com.myproject.postproject.repository

import com.myproject.postproject.domain.Comment
import com.myproject.postproject.domain.CourseInfo
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
class CommentRepository(
    private val em :EntityManager
) {


    fun save(comment: Comment){
        em.persist(comment)
    }

    fun remove(comment: Comment){
        em.remove(comment)
    }


    fun findOne(id :Long): Comment? {
        return em.find(Comment::class.java, id)
    }



    fun findAll(): MutableList<Comment>? {
        return em.createQuery("select c from Comment c", Comment::class.java)
            .resultList
    }

}