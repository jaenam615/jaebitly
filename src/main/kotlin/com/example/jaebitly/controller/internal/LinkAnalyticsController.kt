package com.example.jaebitly.controller.internal

import com.example.jaebitly.application.GetLinkAnalyticsUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class LinkAnalyticsController(
    private val getLinkAnalyticsUseCase: GetLinkAnalyticsUseCase,
) {
    /**
     * Internal analytics endpoint.
     * Not part of public MVP.
     */
    @GetMapping("/internal/links/{key}/analytics")
    fun getLinkAnalytics(
        @PathVariable("key") key: String,
    ): LinkAnalyticsResponse {
        val statView = getLinkAnalyticsUseCase.getLinkAnalytics(key = key)
        return LinkAnalyticsResponse(
            shortKey = statView.shortKey,
            count = statView.count,
            lastOccurredAt = statView.lastOccurredAt,
        )
    }
}

data class LinkAnalyticsResponse(
    val shortKey: String,
    val count: Long,
    val lastOccurredAt: Instant,
)
