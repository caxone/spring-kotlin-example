package br.com.ahi.aula01.features.todos

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/todos")
class TodoHttpV1Controller {
    @Autowired
    private lateinit var todoService: TodoService

    @GetMapping
    fun index() = todoService.listAll().map { todo -> todo.toSimpleResponse()}

    @PostMapping
    @Validated
    fun create(@Valid @RequestBody params: TodoParams) = todoService.create(params)

    @GetMapping("/{id}")
    fun show(@PathVariable id: Long) = todoService.show(id).toResponse()

    @PutMapping("/{id}")
    @Validated
    fun update(@Valid @PathVariable id: Long, @RequestBody params: TodoParams) = todoService.update(id, params).toResponse()

    @DeleteMapping("/{id}")
    fun destroy(@PathVariable id: Long) = todoService.destroy(id)

}