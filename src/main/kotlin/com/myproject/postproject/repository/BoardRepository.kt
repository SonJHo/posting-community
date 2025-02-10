package com.myproject.postproject.repository

import com.myproject.postproject.domain.Board
import com.myproject.postproject.domain.Member
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Repository
class BoardRepository (
    private val em :EntityManager
){

    fun createBoard(name: String, member: Member): Board {
        return Board(name = name, createBy = member.name, member = member
            , date = LocalDateTime.now())
    }

    @Transactional
    fun save(board: Board){
        em.persist(board)
    }

    @Transactional
    fun remove(board: Board){
        em.remove(board)
    }


    fun findById(id :Long): Board? {
        return em.find(Board::class.java, id)
    }


    fun findAll(): MutableList<Board>? {
        return em.createQuery("select b from Board b", Board::class.java)
            .resultList
    }

}