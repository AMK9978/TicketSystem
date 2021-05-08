package com.balloon.balloonet.repos

import com.balloon.balloonet.models.Subscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriptionRepo : JpaRepository<Subscription, Long> {
    fun findByTicketId(ticketId: Long): List<Subscription>
    fun findByUserId(userId: Long): List<Subscription>
    fun findByUserIdAndNotified(userId: Long, notified: Boolean): List<Subscription>
    fun findByUserIdAndTicketId(userId: Long, ticketId: Long): Subscription?
}