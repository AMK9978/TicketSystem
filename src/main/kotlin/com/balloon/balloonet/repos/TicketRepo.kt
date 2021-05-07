package com.balloon.balloonet.repos

import com.balloon.balloonet.models.Ticket
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.awt.print.Pageable

@Repository
interface TicketRepo : JpaRepository<Ticket, Long> {

    fun findAllBySeen(seen: Boolean): List<Ticket>
    fun findAllByUserId(userId: Long): List<Ticket>
    fun findAllAndSeen(userId: Long, seen: Boolean): List<Ticket>
    fun findAllPageable(pageable: Pageable): List<Ticket>

}