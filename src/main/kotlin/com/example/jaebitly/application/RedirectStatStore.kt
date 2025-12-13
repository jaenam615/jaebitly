package com.example.jaebitly.application

import com.example.jaebitly.domain.ShortKey
import com.example.jaebitly.domain.stat.RedirectStat

interface RedirectStatStore {
    fun save(stat: RedirectStat)

    fun findByShortKey(shortKey: ShortKey): RedirectStat?
}
