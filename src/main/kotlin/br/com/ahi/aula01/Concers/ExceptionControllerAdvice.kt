package br.com.ahi.aula01.Concers

import br.com.ahi.aula01.core.MessageSerializer
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice

class ExceptionControllerAdvice {
    @ExceptionHandler(BadRequestExcpetion::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(e: BadRequestExcpetion) = MessageSerializer(400, messages = listOf(e.message as String))

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): MessageSerializer {
        val errors = mutableListOf<String>()
        ex.bindingResult.fieldErrors.forEach { error ->
            error.defaultMessage?.let { errors.add(it) }
        }
        return MessageSerializer(400, messages = errors.toList())
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(e: NotFoundException) = MessageSerializer(404, messages = listOf(e.message as String))

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(e: UnauthorizedException) = MessageSerializer(401, messages = listOf(e.message as String))

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleRuntimeException(e: RuntimeException) = MessageSerializer(500, messages = listOf(e.message as String))

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleRuntimeException(e: Exception) = MessageSerializer(404, messages = listOf(e.message as String))
}