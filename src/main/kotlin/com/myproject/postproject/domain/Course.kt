package com.myproject.postproject.domain

import jakarta.persistence.*


@Entity
class Course (

    @Id @GeneratedValue
    @Column(name = "course_id")
    var id : Long? = null,

    @Column(name = "Course_name", nullable = false)
    var name :String? = null,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    var professor: Professor? = null
)