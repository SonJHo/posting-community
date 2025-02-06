package com.myproject.postproject.repository

import com.myproject.postproject.domain.Course
import com.myproject.postproject.domain.Member
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
class CourseRepository(
    val em : EntityManager
) {

    fun save(course: Course){
        em.persist(course)
    }

    fun remove(course: Course){
        em.remove(course)
    }

    fun findOne(id :Long): Course? {
        return em.find(Course::class.java, id)
    }

    fun findByName(name : String): MutableList<Course>? {
        return em.createQuery("select c from Course c Where c.name = :name", Course::class.java)
            .setParameter("name", name)
            .resultList
    }

    fun findAll(): MutableList<Course>? {
        return em.createQuery("select c from Course c", Course::class.java)
            .resultList
    }


}