package com.example.jaebitly.domain

import java.util.UUID

@JvmInline
value class ShortKey(val value: String) {
    companion object {
        fun generate(): ShortKey {
            val raw = UUID.randomUUID().toString().replace("-", "")
            return ShortKey(raw.substring(0, 6))
        }
    }
}