package com.myproject.postproject.service

import com.myproject.postproject.domain.Course
import com.myproject.postproject.domain.CourseInfo
import com.myproject.postproject.domain.Member
import com.myproject.postproject.repository.CourseInfoRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime


@Service
class CourseService (
    private val courseInfoRepository: CourseInfoRepository
){

// 쒯
//    fun createCourseInfo(member: Member, course: Course){
//        val courseInfo = CourseInfo(member = member, course = course, date = LocalDate.now())
//        courseInfoRepository.save(courseInfo)
//    }


}