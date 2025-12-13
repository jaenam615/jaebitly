package com.example.jaebitly.application

import com.example.jaebitly.domain.ShortKey
import com.example.jaebitly.domain.event.RedirectEvent
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event
import java.time.Instant
import java.time.LocalDateTime

@Component
class RedirectUseCase(
    private val linkRepository: LinkRepository,
    private val eventPublisher: RedirectEventPublisher,
) {
    fun execute(shortKey: String): RedirectResult {
        val key = ShortKey(shortKey)

        val originalUrl =
            linkRepository.findByShortKey(shortKey = key)
                ?: throw ShortLinkNotFoundException()

        eventPublisher.publish(event = RedirectEvent(shortKey = key.value, occurredAt = Instant.now()))

        return RedirectResult(
            targetUrl = originalUrl.value,
        )
    }
}

data class RedirectResult(
    val targetUrl: String,
)
