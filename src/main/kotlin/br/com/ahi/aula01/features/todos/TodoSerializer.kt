package br.com.ahi.aula01.features.todos

import br.com.ahi.aula01.domain.Todo
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class TodoSimpleSerializer(
    val id: Long?,
    val title: String,
    val wasCompleted: Boolean?
)

data class ToSerializer(
    val id: Long?,
    val title: String,
    val description: String,
    val wasCompleted: Boolean?,
    val createdAt: LocalDateTime?
)

data class TodoParams(
    @field:NotBlank
    val title: String,
    val description: String,
    @field:NotNull
    val wasCompleted: Boolean? = false,
)

fun Todo.toSimpleResponse() = TodoSimpleSerializer(
    id = id,
    title = title,
    wasCompleted = wasCompleted
)

fun Todo.toResponse() = ToSerializer(
    id = id,
    title = title,
    description = description,
    wasCompleted = wasCompleted,
    createdAt = createdAt,
)