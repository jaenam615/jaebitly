package com.example.jaebitly.application

import com.example.jaebitly.domain.RedirectRequestContext
import com.example.jaebitly.domain.ShortKey
import com.example.jaebitly.domain.event.RedirectEvent
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class RedirectUseCase(
    private val linkRepository: LinkRepository,
    private val eventPublisher: RedirectEventPublisher,
) {
    fun execute(
        shortKey: String,
        context: RedirectRequestContext,
    ): RedirectResult {
        val key = ShortKey(shortKey)

        val originalUrl =
            linkRepository.findByShortKey(shortKey = key)
                ?: throw ShortLinkNotFoundException()

        eventPublisher.publish(
            event =
                RedirectEvent(
                    shortKey = key.value,
                    occurredAt = Instant.now(),
                    ip = context.ip,
                    userAgent = context.userAgent,
                ),
        )

        return RedirectResult(
            targetUrl = originalUrl.value,
        )
    }
}

data class RedirectResult(
    val targetUrl: String,
)
