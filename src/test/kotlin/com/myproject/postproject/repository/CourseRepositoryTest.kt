package com.myproject.postproject.repository

import com.myproject.postproject.domain.Course
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
class CourseRepositoryTest @Autowired constructor(
    private val courseRepository: CourseRepository,
    private val em : EntityManager
) {

    @Test
    fun 강좌저장(){
    //given
        val course = Course()
        course.name = "컴퓨터구조론"
        //when
        em.persist(course)
        em.flush()
    //then
        Assertions.assertThat(course).isEqualTo(courseRepository.findOne(course.id!!))
    }

    @Test
    fun 강좌삭제(){
        //given
        val course = Course()
        course.name = "컴퓨터구조론"
        //when
        em.persist(course)
        em.flush()
        em.remove(course)
        val courses = courseRepository.findByName("컴퓨터구조론")
        //then
        Assertions.assertThat(courses!!.size).isSameAs(0)
    }


}