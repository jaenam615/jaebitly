package com.example.jaebitly.domain.event

import java.time.Instant

data class RedirectEvent(
    val shortKey: String,
    val occurredAt: Instant,
    val ip: String?,
    val userAgent: String?,
)
