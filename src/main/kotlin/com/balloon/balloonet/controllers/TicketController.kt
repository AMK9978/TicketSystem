package com.balloon.balloonet.controllers

import com.balloon.balloonet.exceptions.ResourceNotFoundException
import com.balloon.balloonet.models.MyUserDetails
import com.balloon.balloonet.models.Ticket
import com.balloon.balloonet.models.TicketToTicket
import com.balloon.balloonet.models.User
import com.balloon.balloonet.repos.RoleRepo
import com.balloon.balloonet.repos.TicketRepo
import com.balloon.balloonet.repos.TicketToTicketRepo
import com.balloon.balloonet.util.Status
import com.balloon.balloonet.util.isAdminOrSupporter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.security.auth.message.AuthException


@RestController
class TicketController {

    @Autowired
    lateinit var ticketRepo: TicketRepo

    @Autowired
    lateinit var roleRepo: RoleRepo

    @Autowired
    lateinit var ticketToTicketRepo: TicketToTicketRepo


    /**
     * Get ticket elements from user and
     * create a ticket
     */
    @PostMapping("/leave_ticket")
    fun leaveTicket(
        @RequestParam(value = "title", defaultValue = "") title: String,
        @RequestParam(value = "content") content: String,
        @RequestParam(value = "severity") severity: Int,
        @RequestParam(value = "ticket_id") ticket_id: Long?
    ): Status {
        val user = getAuthenticatedUser()
        val ticket = Ticket(user.id, title, content, severity = severity)

        return try {
            ticketRepo.save(ticket)
            if (ticket_id != null) {
                if (isAdminOrSupporter(user, roleRepo) || isMyTopic(user, ticket_id)) {
                    val ticketToTicket = TicketToTicket(ticket_id, ticket.id)
                    ticketToTicketRepo.save(ticketToTicket)
                } else {
                    Status.FAILURE
                }
            }
            Status.SUCCESS
        } catch (exception: Exception) {
            Status.FAILURE
        }

    }

    private fun isMyTopic(user: User, ticketId: Long): Boolean {
        val ticket = ticketRepo.findById(ticketId).get()
        return ticket.userId == user.id
    }

    private fun getAuthenticatedUser(): User {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal !is MyUserDetails) {
            throw AuthException()
        }
        return principal.user
    }

    /**
     * Get ticket content by admin/user
     */
    @PostMapping("/get_ticket")
    fun getTicket(@RequestParam(value = "ticket_id") ticketId: Long): Ticket {
        try {
            val ticket: Ticket = ticketRepo.findById(ticketId).get()
            val user = getAuthenticatedUser()
//            if (user.level == userLevel[USER]!! && ticket.userId != user.id) {
//                throw AuthException()
//            }
            return ticket
        } catch (exception: Exception) {
            throw ResourceNotFoundException()
        }
    }

    /**
     * Get unseen tickets
     */
    @GetMapping("/new_tickets")
    fun getNewTickets(): List<Ticket> {
        val user = getAuthenticatedUser()
//        if (user.level == userLevel[USER]!!) {
//            throw AuthException()
//        }
        //TODO: Return unseen tickets
        return ticketRepo.findAllBySeen(false)
    }


    /**
     * Get all tickets
     */
    @GetMapping("/tickets")
    fun getTickets(): MutableList<Ticket> {
        return ticketRepo.findAll()
    }

    /**
     * Get tickets by user
     */
    @GetMapping("/my_tickets")
    fun getMyTickets(): List<Ticket> {
        val user = getAuthenticatedUser()
        //TODO: Return those tickets which are left by current user
        ticketRepo.findAllByUserIdAndSeen(user.id, false).stream()
            .map {
                it.seen = true
            }
        return ticketRepo.findAllByUserId(user.id)
    }

    /**
     * Get updated tickets by user
     */
    @GetMapping("/updated_tickets")
    public fun getMyUpdatedTickets(): List<Ticket> {
        //TODO: Return those tickets which are got ticket in reply
        val user = getAuthenticatedUser()
        //TODO: Return user's tickets
//        ticketRepo.findAllByUserId(user.id).stream()
//            .map(ticket -> ticket)
//        return ticketRepo.findAllByUserIdAndSeen(user.id, false)
        return arrayListOf()
    }

    /**
     * Update tickets by user
     */
    @PostMapping("/update_ticket")
    fun updateTicket(
        @RequestParam(value = "id") id: Long,
        @RequestParam(value = "title") title: String,
        @RequestParam(value = "content") content: String,
        @RequestParam(value = "severity") severity: Int
    ): Status {
        val user = getAuthenticatedUser()
        val ticket = ticketRepo.findById(id).get()
        if (ticket.userId != user.id) {
            return Status.FAILURE
        }
        ticket.content = content
        ticket.title = title
        ticket.severity = severity
        ticketRepo.save(ticket)
        return Status.SUCCESS
    }

    /**
     * Delete tickets by user/admin
     */
    @PostMapping("/delete_tickets")
    fun deleteTickets(@RequestParam ids: LongArray) {
        val user: User = getAuthenticatedUser()
        for (id in ids) {
            val ticket = ticketRepo.findById(id).get()
//            if (user.level == userLevel[ADMIN] || user.id == ticket.userId) {
//                ticketRepo.deleteById(id)
//            }
        }
    }

}