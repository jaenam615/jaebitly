package com.example.jaebitly.infrastructure

import com.example.jaebitly.application.RedirectEventConsumer
import com.example.jaebitly.application.RedirectEventPublisher
import com.example.jaebitly.domain.event.RedirectEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class LogRedirectEventPublisher(
    private val redirectEventConsumer: RedirectEventConsumer,
) : RedirectEventPublisher {
    @Async
    override fun publish(event: RedirectEvent) {
        redirectEventConsumer.consume(event)
    }
}
