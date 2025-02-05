package com.myproject.postproject.repository

import com.myproject.postproject.domain.Course
import com.myproject.postproject.domain.CourseInfo
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository


@Repository
class CourseInfoRepository(
    private val em : EntityManager
) {

    fun save(courseInfo: CourseInfo){
        em.persist(courseInfo)
    }

    fun remove(courseInfo: CourseInfo){
        em.remove(courseInfo)
    }


    fun findOne(id :Long): CourseInfo? {
        return em.find(CourseInfo::class.java, id)
    }




    fun findAll(): MutableList<CourseInfo>? {
        return em.createQuery("select c from CourseInfo c", CourseInfo::class.java)
            .resultList
    }
}