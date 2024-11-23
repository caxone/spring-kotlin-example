package br.com.ahi.aula01

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class Aula01ApplicationTests {

	@Test
	fun contextLoads() {
	}

}
