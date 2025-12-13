package com.example.jaebitly.application

import com.example.jaebitly.domain.ShortKey
import org.springframework.stereotype.Component

@Component
class RedirectUseCase (
    private val linkRepository: LinkRepository,
    private val eventPublisher: RedirectEventPublisher
){
    fun execute(shortKey: String): RedirectResult {
         val key = ShortKey(shortKey)

        val originalUrl = linkRepository.findByShortKey(shortKey = key)
            ?: throw ShortLinkNotFoundException()

        //TODO: 추후 구현체 교체
        eventPublisher.publish(shortKey = key)

        return RedirectResult(
            targetUrl = originalUrl.value
        )
    }
}

data class RedirectResult(
    val targetUrl: String
)