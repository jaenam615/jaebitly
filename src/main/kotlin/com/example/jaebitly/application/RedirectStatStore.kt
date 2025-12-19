package com.example.jaebitly.application

import com.example.jaebitly.domain.ShortKey
import com.example.jaebitly.domain.stat.RedirectStat

/**
 * Internal store for redirect analytics.
 * Implementation may change or be removed.
 */
interface RedirectStatStore {
    fun save(stat: RedirectStat)

    fun findByShortKey(shortKey: ShortKey): RedirectStat?
}
