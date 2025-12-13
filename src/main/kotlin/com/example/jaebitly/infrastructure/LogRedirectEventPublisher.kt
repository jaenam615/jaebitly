package com.example.jaebitly.infrastructure

import com.example.jaebitly.application.RedirectEventPublisher
import com.example.jaebitly.domain.ShortKey
import com.example.jaebitly.domain.event.RedirectEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LogRedirectEventPublisher : RedirectEventPublisher {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun publish(event: RedirectEvent) {
        log.info("redirect_event shortKey={}, occurredAt{}", event.shortKey, event.occurredAt)
    }
}
