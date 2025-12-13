package com.example.jaebitly.infrastructure

import com.example.jaebitly.application.LinkRepository
import com.example.jaebitly.domain.OriginalUrl
import com.example.jaebitly.domain.ShortKey
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryLinkRepository: LinkRepository {
    private val store = ConcurrentHashMap<String, String>()

    override fun save(shortKey: ShortKey, originalUrl: OriginalUrl) {
        store[shortKey.value] = originalUrl.value
    }

    override fun findByShortKey(shortKey: ShortKey): OriginalUrl? {
        val originalUrl = store[shortKey.value] ?: return null
        return OriginalUrl.from(originalUrl)
    }
}