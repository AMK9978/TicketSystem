package com.balloon.balloonet.models

import javax.persistence.*


@Entity
@Table(name = "subscription",uniqueConstraints = [UniqueConstraint(columnNames = ["ticket_id", "user_id"])])
class Subscription(@Column(name = "ticket_id") var ticketId: Long, @Column(name = "user_id") var userId: Long,
                   var notified:Boolean) {
    @Id
    @Column(name = "subscription_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}