package br.com.ahi.aula01.features.todos

import br.com.ahi.aula01.TestcontainersConfiguration
import br.com.ahi.aula01.domain.Todo
import br.com.ahi.aula01.domain.TodoRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.spec.style.DescribeSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration::class)
class TodoHttpV1ControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val todoRepository: TodoRepository,
    @Autowired private val objectMapper: ObjectMapper
) : DescribeSpec({
    describe("GET /v1/todos") {
        context("when there is no data") {
            it("should respond with status ok") {
                val response = mockMvc.get("/v1/todos")

                response.andExpect { status { isOk() } }
            }

            it("should return an empty list") {
                val response = mockMvc.get("/v1/todos")

                response.andExpect {jsonPath("$") { isArray() } }
                response.andExpect {jsonPath("$.length()") { value(0) } }
            }

        }
        context("when data is data") {
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

            it("should respond status ok") {
                val response = mockMvc.get("/v1/todos")

                response.andExpect { status { isOk() } }
            }
            it("should respond todo items") {
                val response = mockMvc.get("/v1/todos")
                response
                    .andExpect { jsonPath("$[0].title") {value(todo1.title)} }
                    .andExpect { jsonPath("$[0].id") {value(todo1.id)} }
                    .andExpect { jsonPath("$[0].wasCompleted") {value(todo1.wasCompleted)} }
                    .andExpect { jsonPath("$[0].description") {doesNotExist()} }
                    .andExpect { jsonPath("$[0].createdAt") {doesNotExist()} }
            }
        }
    }

    describe("POST /v1/todos") {
        context("when the params are invalid") {
            val params = emptyMap<String, String>().toMutableMap()
            params["description"] = "foo"
            params["title"] = ""

            it("should return the bad request status") {
                val response = mockMvc.post("/v1/todos") {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(params)
                }
                response.andExpect { status { isBadRequest() } }
            }
            it("should return the error's message") {
                val response = mockMvc.post("/v1/todos") {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(params)
                }

                response
                    .andExpect { jsonPath("$.code") { value(400) } }
                    .andExpect { jsonPath("$.messages") { isArray() } }
                    .andExpect { jsonPath("$.messages[0]") { value("must not be blank") } }
            }


        }

        context("when the params are valid") {
            val params = emptyMap<String, String>().toMutableMap()
            params["description"] = "foo"
            params["title"] = "teste"
            it("should return status ok") {
                val response = mockMvc.post("/v1/todos") {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(params)
                }
                response.andExpect { status { isOk() } }
            }

            it("should return the item") {
                val response = mockMvc.post("/v1/todos") {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(params)
                }
                response
                    .andExpect { jsonPath("$.title") { value("teste")} }
                    .andExpect { jsonPath("$.wasCompleted") {value(false)} }
                    .andExpect { jsonPath("$.description") {value("foo")} }
            }
        }


    }

})
