package com.example.jaebitly.application

import com.example.jaebitly.domain.ShortKey
import com.example.jaebitly.domain.stat.RedirectStatView
import org.springframework.stereotype.Component

@Component
class GetLinkAnalyticsUseCase(
    private val redirectStatStore: RedirectStatStore,
) {
    fun getLinkAnalytics(key: String): RedirectStatView {
        val shortKey = ShortKey(key)
        val stats = redirectStatStore.findByShortKey(shortKey) ?: throw ShortLinkNotFoundException()

        return RedirectStatView(
            shortKey = shortKey.value,
            count = stats.count,
            lastOccurredAt = stats.lastOccurredAt,
        )
    }
}
