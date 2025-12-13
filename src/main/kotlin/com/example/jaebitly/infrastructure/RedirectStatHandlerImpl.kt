package com.example.jaebitly.infrastructure

import com.example.jaebitly.application.RedirectStatHandler
import com.example.jaebitly.application.RedirectStatStore
import com.example.jaebitly.domain.ShortKey
import com.example.jaebitly.domain.event.RedirectEvent
import com.example.jaebitly.domain.stat.RedirectStat
import org.springframework.stereotype.Component

@Component
class RedirectStatHandlerImpl(
    private val statStore: RedirectStatStore,
) : RedirectStatHandler {
    override fun handle(event: RedirectEvent) {
        val key = ShortKey(event.shortKey)

        val existing = statStore.findByShortKey(key)
        val updated =
            existing?.incrementCount(event.occurredAt)
                ?: RedirectStat.initial(
                    shortKey = key,
                    occurredAt = event.occurredAt,
                )

        statStore.save(updated)
    }
}
