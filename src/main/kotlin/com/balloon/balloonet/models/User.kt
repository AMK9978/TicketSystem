package com.balloon.balloonet.models

import javax.persistence.*
import javax.validation.constraints.NotBlank


@Entity
@Table(name = "users")
class User(
    @NotBlank
    @Column(unique = true) var email: String, @NotBlank var password: String,
    var level: Int = 0, var name: String = "A User"
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (email != other.email) return false
        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + level
        return result
    }

    override fun toString(): String {
        return "User(email='$email', password='$password', id=$id, name='$name', level=$level)"
    }


}