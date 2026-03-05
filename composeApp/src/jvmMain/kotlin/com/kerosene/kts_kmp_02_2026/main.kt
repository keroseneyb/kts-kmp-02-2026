package com.kerosene.kts_kmp_02_2026

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kerosene.kts_kmp_02_2026.app.App
import com.kerosene.kts_kmp_02_2026.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Ktskmp022026",
        ) {
            App()
        }
    }
}