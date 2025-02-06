package com.myproject.postproject.repository

import com.myproject.postproject.domain.Board
import com.myproject.postproject.domain.Post
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository


@Repository
class PostRepository (
    private  val em :EntityManager
){

    fun save(post: Post){
        em.persist(post)
    }

    fun remove(post: Post){
        em.remove(post)
        em.flush()
        em.clear()
    }


    fun findOne(id :Long): Post? {
        return em.find(Post::class.java, id)
    }


    fun findAll(): MutableList<Post>? {
        return em.createQuery("select p from Post p", Post::class.java)
            .resultList
    }
}