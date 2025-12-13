package com.example.jaebitly.infrastructure

import com.example.jaebitly.application.RedirectStatHandler
import com.example.jaebitly.domain.ShortKey
import com.example.jaebitly.domain.event.RedirectEvent
import com.example.jaebitly.domain.stat.RedirectStat
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryRedirectStatHandler : RedirectStatHandler {
    private val stats: MutableMap<ShortKey, RedirectStat> = ConcurrentHashMap()

    override fun handle(event: RedirectEvent) {
        val key = ShortKey(event.shortKey)

        stats.compute(key) { _, existing ->
            existing?.incrementCount(event.occurredAt)
                ?: RedirectStat.initial(
                    shortKey = key,
                    occurredAt = event.occurredAt,
                )
        }
    }

    fun getStat(shortKey: ShortKey): RedirectStat? = stats[shortKey]

    fun clear() {
        stats.clear()
    }
}
