package com.example.jaebitly.infrastructure

import com.example.jaebitly.application.RedirectEventPublisher
import com.example.jaebitly.domain.event.RedirectEvent
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class LogRedirectEventPublisher : RedirectEventPublisher {
    private val log = LoggerFactory.getLogger(javaClass)

    @Async
    override fun publish(event: RedirectEvent) {
        log.info(
            "redirect_event shortKey={}, occurredAt={}, ip={}, user-agent={}",
            event.shortKey,
            event.occurredAt,
            event.ip,
            event.userAgent,
        )
    }
}
