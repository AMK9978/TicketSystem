package com.balloon.balloonet.controllers

import com.balloon.balloonet.exceptions.CloseTicketException
import com.balloon.balloonet.exceptions.ResourceNotFoundException
import com.balloon.balloonet.models.*
import com.balloon.balloonet.repos.RoleRepo
import com.balloon.balloonet.repos.SubscriptionRepo
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
import java.util.stream.Collectors
import javax.security.auth.message.AuthException


@RestController
class TicketController {

    @Autowired
    lateinit var ticketRepo: TicketRepo

    @Autowired
    lateinit var roleRepo: RoleRepo

    @Autowired
    lateinit var ticketToTicketRepo: TicketToTicketRepo


    @Autowired
    lateinit var subscriptionRepo: SubscriptionRepo


    /**
     * Get ticket elements from user and
     * create a ticket
     */
    @PostMapping("/leave_ticket")
    fun leaveTicket(
        @RequestParam(value = "title", defaultValue = "") title: String,
        @RequestParam(value = "content") content: String,
        @RequestParam(value = "severity") severity: Int,
        @RequestParam(value = "status") status: String?,
        @RequestParam(value = "ticket_id") ticket_id: Long?
    ): Status {
        val user = getAuthenticatedUser()
        val ticket = Ticket(user.id, title, content, severity = severity)

        return try {
            if (ticket_id != null) {
                val parentTicket = ticketRepo.findById(ticket_id).get()
                if (parentTicket.status == STATUS.CLOSED) {
                    throw CloseTicketException()
                }
            }
            ticketRepo.save(ticket)
            if (ticket_id != null) {
                subscribe(ticket_id, user.id)
                if (isAdminOrSupporter(user, roleRepo) || isMyTopic(user, ticket_id)) {
                    val ticketToTicket = TicketToTicket(ticket_id, ticket.id)
                    ticketToTicketRepo.save(ticketToTicket)
                    val parentTicket = ticketRepo.findById(ticket_id).get()
                    if (isAdminOrSupporter(user, roleRepo)) {
                        if (STATUS.CLOSED == status?.let { STATUS.valueOf(it) }) {
                            parentTicket.status = STATUS.valueOf(CLOSED)
                            ticketRepo.save(parentTicket)
                        }
                        updateSubscribers(parentTicket, notifyStaffs = false, notifyCreator = true)
                    } else {
                        updateSubscribers(parentTicket, notifyStaffs = true, notifyCreator = false)
                    }
                } else {
                    val ticketToTicket = TicketToTicket(ticket.id, ticket.id)
                    ticketToTicketRepo.save(ticketToTicket)
                    Status.FAILURE
                }
            } else {
                subscribe(ticket.id, user.id)
            }
            Status.SUCCESS
        } catch (exception: Exception) {
            Status.FAILURE
        }

    }

    private fun updateSubscribers(ticket: Ticket, notifyStaffs: Boolean, notifyCreator: Boolean) {
        subscriptionRepo.findByTicketId(ticketId = ticket.id).forEach {
            if (ticket.userId == it.userId) {
                it.notified = notifyCreator
            } else {
                it.notified = notifyStaffs
            }
            subscriptionRepo.save(it)
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
    fun getTicketThread(@RequestParam(value = "ticket_id") ticketId: Long): MutableList<Ticket> {
        try {
            val ticket: Ticket = ticketRepo.findById(ticketId).get()
            val user = getAuthenticatedUser()
            if (!isAdminOrSupporter(user, roleRepo) && ticket.userId != user.id) {
                throw AuthException()
            }
            val ticketParents = getTicketParents(ticket)
            if (ticket.userId != user.id) {
                ticketParents.map {
                    it.seen = true
                    ticketRepo.save(it)
                }
            }
            return ticketParents
        } catch (exception: Exception) {
            throw ResourceNotFoundException()
        }
    }

    fun getTicketParents(ticket: Ticket): MutableList<Ticket> {
        val ticketTotTicket = ticketToTicketRepo.findByReplyId(replyId = ticket.id)
        return ticketToTicketRepo.findAllByTicketId(ticketId = ticketTotTicket.ticketId).stream().map {
            ticketRepo.findById(it.replyId).get()
        }.collect(Collectors.toList())
    }

    /**
     * Get unseen tickets
     */
    @GetMapping("/new_tickets")
    fun getNewTickets(): List<Ticket> {
        val user = getAuthenticatedUser()
        if (!isAdminOrSupporter(user, roleRepo)) {
            throw AuthException()
        }
        return ticketRepo.findAllBySeen(false).filter {
            it.userId == user.id
        }.map {
            val topic = ticketToTicketRepo.findByReplyId(it.id)
            ticketRepo.findById(topic.ticketId).get()
        }.toSet().toList()
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
        return ticketRepo.findAllByUserId(user.id)
    }

    /**
     * Get updated tickets by user
     */
    @GetMapping("/updated_tickets")
    fun getMyUpdatedTickets(): List<Ticket> {
        val user = getAuthenticatedUser()
        return subscriptionRepo.findByUserIdAndNotified(userId = user.id, true).map {
            ticketRepo.findById(it.ticketId).get()
        }
    }


    fun subscribe(ticketId: Long, userId: Long) {
        if (subscriptionRepo.findByUserIdAndTicketId(userId, ticketId) != null) {
            return
        }
        val subscription = Subscription(ticketId, userId, false)
        subscriptionRepo.save(subscription)
    }

    /**
     * Update tickets by user
     */
    @PostMapping("/update_ticket")
    fun updateTicket(
        @RequestParam(value = "id") id: Long,
        @RequestParam(value = "title") title: String,
        @RequestParam(value = "content") content: String,
        @RequestParam(value = "severity") severity: Int,
        @RequestParam(value = "status") status: String?
    ): Status {
        val user = getAuthenticatedUser()
        val ticket = ticketRepo.findById(id).get()
        if (ticket.userId != user.id) {
            return Status.FAILURE
        }
        ticket.content = content
        ticket.title = title
        ticket.severity = severity
        status?.let {
            if (isAdminOrSupporter(user, roleRepo)) {
                val parentTicket = getParentTicket(ticket.id)
                if (it == CLOSED) {
                    parentTicket.status = STATUS.CLOSED
                } else if (it == OPEN) {
                    parentTicket.status = STATUS.OPEN
                }
                ticketRepo.save(parentTicket)
            }
        }
        ticketRepo.save(ticket)
        return Status.SUCCESS
    }


    fun getParentTicket(replyTicketId: Long): Ticket {
        return ticketRepo.findById(ticketToTicketRepo.findByReplyId(replyTicketId).ticketId).get()
    }

    /**
     * Delete tickets by user/admin
     */
    @PostMapping("/delete_tickets")
    fun deleteTickets(@RequestParam ids: LongArray) {
        val user: User = getAuthenticatedUser()
        for (id in ids) {
            val ticket = ticketRepo.findById(id).get()
            if (isAdminOrSupporter(user, roleRepo) || user.id == ticket.userId) {
                ticketRepo.deleteById(id)
            }
        }
    }

}