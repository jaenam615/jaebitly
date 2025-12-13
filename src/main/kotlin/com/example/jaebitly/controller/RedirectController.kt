package com.example.jaebitly.controller

import com.example.jaebitly.application.RedirectUseCase
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RedirectController(
    private val redirectUseCase: RedirectUseCase,
) {
    @GetMapping("/{shortKey}")
    fun redirect(
        @PathVariable("shortKey") shortKey: String,
    ): ResponseEntity<Void> {
        val result = redirectUseCase.execute(shortKey)

        return ResponseEntity
            .status(HttpStatus.FOUND)
            .header(HttpHeaders.LOCATION, result.targetUrl)
            .build()
    }
}
