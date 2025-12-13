package com.example.jaebitly.application

import com.example.jaebitly.domain.event.RedirectEvent

interface RedirectEventConsumer {
    fun consume(event: RedirectEvent)
}
