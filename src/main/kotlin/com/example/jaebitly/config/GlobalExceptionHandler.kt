package com.example.jaebitly.config

import com.example.jaebitly.application.ShortLinkNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ShortLinkNotFoundException::class)
    fun handleNotFound(): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }
}