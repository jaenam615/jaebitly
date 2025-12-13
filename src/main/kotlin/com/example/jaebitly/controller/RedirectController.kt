package com.example.jaebitly.controller

import com.example.jaebitly.application.RedirectUseCase
import com.example.jaebitly.domain.RedirectRequestContext
import jakarta.servlet.http.HttpServletRequest
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
        request: HttpServletRequest,
    ): ResponseEntity<Void> {
        val ip =
            request
                .getHeader("X-Forwarded-For")
                ?.split(",")
                ?.first()
                ?.trim()
                ?: request.remoteAddr
        val agent = request.getHeader("User-Agent")
        val context =
            RedirectRequestContext(
                ip = ip,
                userAgent = agent,
            )

        val result = redirectUseCase.execute(shortKey = shortKey, context = context)

        return ResponseEntity
            .status(HttpStatus.FOUND)
            .header(HttpHeaders.LOCATION, result.targetUrl)
            .build()
    }
}
