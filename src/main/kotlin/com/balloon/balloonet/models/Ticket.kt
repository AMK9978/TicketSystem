package com.balloon.balloonet.models

import javax.persistence.*


@Entity
@Table(name = "tickets")
class Ticket(
        var userId: Long = 0,
        var title: String,
        var content: String,
        var isSeen: Boolean = false,
        var severity: Int = 0,
){
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0


        @ManyToOne(fetch = FetchType.LAZY)
        var ticketToTicket:TicketToTicket? = null

}