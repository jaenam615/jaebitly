package com.example.jaebitly.domain

import java.net.URI

@JvmInline
value class OriginalUrl private constructor(val raw: String) {
    companion object {
        fun from(raw: String): OriginalUrl {
            require(raw.isNotBlank()) { "URL must not be blank" }

            val uri = URI(raw)
            require(uri.scheme == "http" || uri.scheme == "https"){
                "Only HTTP or HTTPS are allowed"
            }

            return OriginalUrl(raw=raw)
        }
    }
}