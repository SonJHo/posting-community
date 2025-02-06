package com.myproject.postproject.domain

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
class Board(
    @Id @GeneratedValue
    @Column(name = "board_id")
    var id: Long? = null,

    @Column(name = "board_name")
    var name: String? = null,

    @Column(name = "create_by")
    var createBy: String? = null,


    var date: LocalDateTime? = null,

    @OneToMany(mappedBy = "board", cascade = [CascadeType.ALL], orphanRemoval = true)
    var postList: MutableList<Post> = mutableListOf(),
)