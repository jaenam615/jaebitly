package com.example.jaebitly.application

import com.example.jaebitly.domain.OriginalUrl
import com.example.jaebitly.domain.ShortKey
import org.springframework.stereotype.Component

@Component
class CreateShortLinkUseCase {

    fun execute(originalUrl: String): CreateShortLinkResult {
        val url = OriginalUrl.from(originalUrl)
        val shortKey = ShortKey.generate()

        return CreateShortLinkResult(
            shortKey = shortKey.value,
            shortUrl = "http://localhost:8080/${shortKey.value}"
        )
    }
}

data class CreateShortLinkResult(
    val shortKey: String,
    val shortUrl: String
)