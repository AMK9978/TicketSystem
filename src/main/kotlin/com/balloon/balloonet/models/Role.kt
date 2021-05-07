package com.balloon.balloonet.models

import javax.persistence.*


@Entity
@Table(name = "roles")
class Role(var name: String) {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}