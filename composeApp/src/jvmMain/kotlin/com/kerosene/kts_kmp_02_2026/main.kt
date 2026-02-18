package com.kerosene.kts_kmp_02_2026

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Ktskmp022026",
    ) {
        App()
    }
}