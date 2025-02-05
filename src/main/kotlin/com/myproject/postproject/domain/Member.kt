package com.myproject.postproject.domain

import jakarta.persistence.*


@Entity
@Table(
    name = "member",
    uniqueConstraints = [
        UniqueConstraint(name = "unique_studentId", columnNames = ["studentId"])
    ]
)
class Member (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id :Long? = null,

    @Column(name = "student_id")
    var studentId : Long? = null,

    @Column(name = "account_id", nullable = false, unique = true)
    var accountId :String? = null,

    @Column(name = "password", nullable = false)
    var password :String? = null,

    @Column(nullable = false)
    var name :String? = null,

    var age :Int? = null,

    var tel :String? = null,

    @Column(name = "email_address")
    var emailAddress :String? = null,

    @Enumerated(EnumType.STRING)
    var grade : Grade? = Grade.STUD

)