package br.com.ahi.aula01

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<Aula01Application>().with(TestcontainersConfiguration::class).run(*args)
}
