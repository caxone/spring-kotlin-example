package br.com.ahi.aula01.domain

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 80)
    var title: String,

    @Column
    var description: String,

    @Column
    var wasCompleted: Boolean? = false,

    @CreationTimestamp
    val createdAt: LocalDateTime? = null,

    @CreationTimestamp
    val updatedAt: LocalDateTime? = null,
)