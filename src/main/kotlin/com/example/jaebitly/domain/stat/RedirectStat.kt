package com.example.jaebitly.domain.stat

import com.example.jaebitly.domain.ShortKey
import java.time.Instant

data class RedirectStat(
    val shortKey: ShortKey,
    val lastOccurredAt: Instant,
    val count: Long,
) {
    fun incrementCount(occurredAt: Instant): RedirectStat =
        copy(
            count = count + 1,
            lastOccurredAt = occurredAt,
        )

    companion object {
        fun initial(
            shortKey: ShortKey,
            occurredAt: Instant,
        ): RedirectStat =
            RedirectStat(
                shortKey = shortKey,
                count = 1,
                lastOccurredAt = occurredAt,
            )
    }
}
