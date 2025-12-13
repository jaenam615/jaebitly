package com.example.jaebitly.application

import com.example.jaebitly.domain.ShortKey

interface RedirectEventPublisher {
    fun publish(shortKey: ShortKey)
}