package br.com.ahi.aula01.domain

import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long> {
    fun findAllByWasCompletedIsOrderByIdDesc(wasCompleted: Boolean): List<Todo>

    fun findAllByOrderByIdDesc(): List<Todo>
}