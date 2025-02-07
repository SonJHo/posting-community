package com.myproject.postproject.domain

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
class Comment(

    @Id @GeneratedValue
    @Column(name = "comment_id")
    var id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null,

    var content: String? = null,

    var date: LocalDateTime? = null,

    var createBy :String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    var post: Post? = null,


    )