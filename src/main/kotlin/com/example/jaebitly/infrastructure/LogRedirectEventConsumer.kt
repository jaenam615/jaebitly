package com.example.jaebitly.infrastructure

import com.example.jaebitly.application.RedirectEventConsumer
import com.example.jaebitly.application.RedirectStatHandler
import com.example.jaebitly.domain.event.RedirectEvent
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LogRedirectEventConsumer(
    private val redirectStatHandler: RedirectStatHandler,
) : RedirectEventConsumer {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun consume(event: RedirectEvent) {
        redirectStatHandler.handle(event)

        log.info(
            "redirect_event shortKey={}, occurredAt={}, ip={}, user-agent={}",
            event.shortKey,
            event.occurredAt,
            event.ip,
            event.userAgent,
        )
    }
}
