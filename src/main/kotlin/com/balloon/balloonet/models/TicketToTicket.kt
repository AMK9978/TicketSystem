package com.balloon.balloonet.models

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "ticket_to_ticket")
class TicketToTicket(
    @NotBlank
    @OnDelete(action = OnDeleteAction.CASCADE)
    var ticketId: Long = 0,
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotBlank
    var replyId: Long = 0,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

}