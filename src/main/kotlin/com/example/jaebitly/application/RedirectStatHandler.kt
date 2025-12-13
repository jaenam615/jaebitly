package com.example.jaebitly.application

import com.example.jaebitly.domain.event.RedirectEvent

interface RedirectStatHandler {
    fun handle(event: RedirectEvent)
}
