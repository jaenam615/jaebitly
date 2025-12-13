package com.example.jaebitly.controller

import com.example.jaebitly.application.CreateShortLinkUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ShortLinkController (
    private val createShortLinkUseCase: CreateShortLinkUseCase
){

    @PostMapping("/links")
    fun create(@RequestBody request: CreateShortLinkRequest): CreateShortLinkResponse {
        val result = createShortLinkUseCase.execute(request.originalUrl)

        return CreateShortLinkResponse(
            shortKey = result.shortKey,
            shortUrl = result.shortUrl
        )
    }

}

data class CreateShortLinkRequest(
    val originalUrl: String,
)

data class CreateShortLinkResponse(
    val shortKey: String,
    val shortUrl: String
)