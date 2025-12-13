package com.example.jaebitly.application

import com.example.jaebitly.domain.ShortKey
import org.springframework.stereotype.Component

@Component
class RedirectUseCase {
    fun execute(shortKey: String): RedirectResult {
         val key = ShortKey(shortKey)

        return RedirectResult(
            targetUrl = "https://google.com"
        )
    }
}

data class RedirectResult(
    val targetUrl: String
)