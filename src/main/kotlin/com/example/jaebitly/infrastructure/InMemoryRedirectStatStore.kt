package com.example.jaebitly.infrastructure

import com.example.jaebitly.application.RedirectStatStore
import com.example.jaebitly.domain.ShortKey
import com.example.jaebitly.domain.stat.RedirectStat
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryRedirectStatStore : RedirectStatStore {
    private val stats = ConcurrentHashMap<ShortKey, RedirectStat>()

    override fun findByShortKey(shortKey: ShortKey): RedirectStat? = stats[shortKey]

    override fun save(stat: RedirectStat) {
        stats[stat.shortKey] = stat
    }
}
