package com.balloon.balloonet.repos

import com.balloon.balloonet.models.TicketToTicket
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TicketToTicketRepo : JpaRepository<TicketToTicket, Long> {

    fun findAllByTicketId(ticketId: Long): List<TicketToTicket>
    fun findAllByReplyId(replyId: Long): List<TicketToTicket>

}