package com.balloon.balloonet.models

import javax.persistence.*


@Entity
@Table(name = "tickets")
class Ticket(
    var userId: Long = 0,
    var title: String,
    var content: String,
    var seen: Boolean = false,
    var status: STATUS = STATUS.OPEN,
    var severity: Int = 0,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

}


const val CLOSED = "CLOSED"
const val OPEN = "OPEN"

enum class STATUS {
    CLOSED,
    OPEN,
}