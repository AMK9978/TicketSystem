package com.balloon.balloonet.models

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "ticket_to_ticket")
class TicketToTicket(
    @NotBlank
    var ticketId: Long = 0,
    @NotBlank
    var replyId: Long = 0,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

}