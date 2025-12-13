package com.example.jaebitly.application

import com.example.jaebitly.domain.OriginalUrl
import com.example.jaebitly.domain.ShortKey

interface LinkRepository {
    fun save(shortKey: ShortKey, originalUrl: OriginalUrl)

    fun findByShortKey(shortKey: ShortKey): OriginalUrl?
}