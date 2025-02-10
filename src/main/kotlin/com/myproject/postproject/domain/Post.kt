package com.myproject.postproject.domain

import jakarta.persistence.*
import java.time.LocalDateTime
import kotlin.time.measureTimedValue


@Entity
class Post(

    @Id @GeneratedValue
    @Column(name = "post_id")
    var id: Long? = null,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null,

    var title: String? = null,

    var createBy : String? = null,

    @Lob
    @Basic(fetch = FetchType.LAZY)
    var content: String? = null,

    var date: LocalDateTime? = null,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    var board: Board? = null,

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableList<Comment> = mutableListOf(),
)