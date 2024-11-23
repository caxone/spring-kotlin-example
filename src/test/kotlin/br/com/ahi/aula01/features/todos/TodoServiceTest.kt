package br.com.ahi.aula01.features.todos

import br.com.ahi.aula01.Concers.NotFoundException
import br.com.ahi.aula01.TestcontainersConfiguration
import br.com.ahi.aula01.domain.Todo
import br.com.ahi.aula01.domain.TodoRepository
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Import(TestcontainersConfiguration::class)
class TodoServiceTest(
    @Autowired private val subject: TodoService,
    @Autowired private val todoRepository: TodoRepository
) : DescribeSpec({

    describe("listAll") {
        describe("when the list is empty") {
            it("should return an empty list") {
                val result = subject.listAll()
                result.shouldBeEmpty()
            }
        }

        describe("when the list has values") {
            val todo01 = Todo(
                title = "teste 1",
                description = "descricao 1",
                wasCompleted = false,
            )

            val todo02 = Todo(
                title = "teste 2",
                description = "descricao 2",
                wasCompleted = false,
            )

            beforeTest {
                todoRepository.save(todo01)
                todoRepository.save(todo02)
            }

            afterTest {
                todoRepository.deleteAll()
            }

            it("should return the list") {
                val result = subject.listAll()
                result.shouldNotBeEmpty()
                result.shouldHaveSize(2)
                result[1].title.shouldBe("teste 1")
                result[1].description.shouldBe("descricao 1")
                result[1].wasCompleted.shouldBe(false)
                result[0].title.shouldBe("teste 2")
                result[0].description.shouldBe("descricao 2")
                result[0].wasCompleted.shouldBe(false)
            }
        }
    }

    describe("create") {
        describe("create") {
            val params = TodoParams(
                title = "titulo teste",
                description = "descricao 1",
                wasCompleted = false,
            )

            it("should  save the todo") {
                val todoItem = subject.create(params)

                todoItem.id.shouldNotBeNull()
                todoItem.title.shouldBe("titulo teste")
                todoItem.description.shouldBe("descricao 1")
                todoItem.wasCompleted.shouldBe(false)
            }
        }
    }

    describe("show") {
        describe("when the item was not found") {
            it("raise an error") {
                shouldThrowExactly<NotFoundException> {
                    subject.show(12345)
                }
            }
        }

        describe("when the item was found") {
            var todo1 = Todo(
                title = "titulo teste",
                description = "descricao 1",
                wasCompleted = false,
            )

            beforeTest {
                todo1 = todoRepository.save(todo1)
            }

            afterTest {
                todoRepository.deleteAll()
            }

            it("return the item") {
                val todoItem = subject.show(todo1.id!!)
                todoItem.id.shouldBe(todo1.id)
                todoItem.title.shouldBe(todo1.title)
                todoItem.description.shouldBe(todo1.description)
                todoItem.wasCompleted.shouldBe(todo1.wasCompleted)
            }


        }
    }

    describe("update") {
        var todo1 = Todo(
            title = "titulo teste",
            description = "descricao 1",
            wasCompleted = false,
        )

        val params =  TodoParams(
            title = "titulo teste asd",
            description = "descricao asd",
            wasCompleted = true
        )

        beforeTest {
            todo1 = todoRepository.save(todo1)
        }

        afterTest {
            todoRepository.deleteAll()
        }


        describe("when the item was not found") {
            it("raise an error") {
                shouldThrowExactly<NotFoundException> {
                    subject.update(12345, params)
                }
            }
        }

        describe("when the item was updated") {

            it("should update the item") {


                val todoItem = subject.update(todo1.id!!, params)
                todoItem.id.shouldBe(todo1.id)
                todoItem.title.shouldBe(params.title)
                todoItem.description.shouldBe(params.description)
                todoItem.wasCompleted.shouldBe(params.wasCompleted)
                todoItem.updatedAt.shouldNotBe(todoItem.createdAt)
            }


        }

    }

    describe("destroy") {

        var todo1 = Todo(
            title = "titulo teste",
            description = "descricao 1",
            wasCompleted = false,
        )


        beforeTest {
            todo1 = todoRepository.save(todo1)
        }

        afterTest {
            todoRepository.deleteAll()
        }


        describe("when the item was not found") {
            it("raise an error") {
                shouldThrowExactly<NotFoundException> {
                    subject.destroy(12345)
                }
            }
        }

        describe("when the item was destryed") {

            it("should update the item") {


                val destroiedTodo = subject.destroy(todo1.id!!)


                destroiedTodo.shouldBe(true)


                shouldThrowExactly<NotFoundException> {
                    subject.show(todo1.id!!)
                }

            }


        }
    }
})
