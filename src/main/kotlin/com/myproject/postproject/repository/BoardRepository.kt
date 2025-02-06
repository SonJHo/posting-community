package com.myproject.postproject.repository

import com.myproject.postproject.domain.Board
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Repository
class BoardRepository (
    private val em :EntityManager
){

    fun createBoard(name: String, createdBy: String): Board {
        return Board(name = name, createBy = createdBy, date = LocalDateTime.now())
    }

    @Transactional
    fun save(board: Board){
        em.persist(board)
    }

    fun remove(board: Board){
        em.remove(board)
    }


    fun findOne(id :Long): Board? {
        return em.find(Board::class.java, id)
    }


    fun findAll(): MutableList<Board>? {
        return em.createQuery("select b from Board b", Board::class.java)
            .resultList
    }

}