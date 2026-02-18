package com.kerosene.kts_kmp_02_2026

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform