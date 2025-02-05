package com.myproject.postproject.domain

import jakarta.persistence.*


@Entity
class Professor (
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    val id : Long? = null,

    val name : String? = null,

    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "member_id")
    val member : Member? = null
)