package com.example.jaebitly.domain.stat

import java.time.Instant

data class RedirectStatView(
    val shortKey: String,
    val count: Long,
    val lastOccurredAt: Instant,
)
