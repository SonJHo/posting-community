package com.myproject.postproject.repository

import com.myproject.postproject.domain.Grade
import com.myproject.postproject.domain.Member
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository


@Repository
class MemberRepository(
    val em: EntityManager
){

    fun save(member : Member){
        em.persist(member)
        em.flush()

    }

    fun remove(member: Member){

        em.remove(member)
    }


    fun findOne(id :Long): Member? {
        return em.find(Member::class.java, id)
    }

    fun findByName(name : String): MutableList<Member> {
        return em.createQuery("select m from Member m Where m.name = :name", Member::class.java)
            .setParameter("name", name)
            .resultList
    }
    fun findByAccountId(accountId :String): Member? {
        return em.createQuery("select m from Member m Where m.accountId = :accountId", Member::class.java)
            .setParameter("accountId", accountId)
            .resultList.firstOrNull()
    }

    fun findByStudentId(studentId : Long): Member? {

        return em.createQuery("SELECT m FROM Member m WHERE m.studentId = :studentId", Member::class.java)
            .setParameter("studentId", studentId)
            .resultList
            .firstOrNull()
    }

    fun findAll(): MutableList<Member> {

        return em.createQuery("select m from Member m", Member::class.java)
            .resultList
    }
}
