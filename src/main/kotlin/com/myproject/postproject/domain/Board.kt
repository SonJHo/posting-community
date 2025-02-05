package com.myproject.postproject.domain

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
class Board(
    @Id @GeneratedValue
    @Column(name = "board_id")
    var id: Long? = null,

    var name: String? = null,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null,


    var date: LocalDateTime? = null,


    )