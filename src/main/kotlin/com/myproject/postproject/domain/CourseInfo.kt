package com.myproject.postproject.domain

import jakarta.persistence.*
import org.springframework.cglib.core.Local
import java.time.LocalDate


@Entity
class CourseInfo (
    @Id @GeneratedValue
    @Column(name = "Course_info_id")
    var id : Long? = null,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    var course: Course? = null,

    var date: LocalDate? = null
)