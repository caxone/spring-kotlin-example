package br.com.ahi.aula01.features.todos

import br.com.ahi.aula01.Concers.NotFoundException
import br.com.ahi.aula01.domain.Todo
import br.com.ahi.aula01.domain.TodoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TodoService(
    @Autowired private val todoRepository: TodoRepository
) {
    fun listAll(): List<Todo>{
        val todos = todoRepository.findAllByOrderByIdDesc()
        return todos
    }

    fun create(params: TodoParams): Todo {
        val todo = Todo(
            title = params.title,
            description = params.description,
            wasCompleted = params.wasCompleted
        )
        return todoRepository.save(todo)
    }

    fun show(id: Long): Todo {
        val todo = todoRepository.findById(id).orElseThrow {
            throw NotFoundException("Tarefa não encontrada")
        }
        return todo
    }

    fun update(id: Long, params: TodoParams): Todo {
        val todo = show(id)

        todo.apply {
            title = params.title
            description = params.description
            wasCompleted = params.wasCompleted
        }

        return todoRepository.save(todo)
    }

    fun destroy(id: Long): Boolean {
        val todo = show(id)

        todoRepository.delete(todo)

        return true
    }
}