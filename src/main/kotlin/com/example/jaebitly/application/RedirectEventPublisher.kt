package com.example.jaebitly.application

import com.example.jaebitly.domain.ShortKey
import com.example.jaebitly.domain.event.RedirectEvent

interface RedirectEventPublisher {
    fun publish(event: RedirectEvent)
}
