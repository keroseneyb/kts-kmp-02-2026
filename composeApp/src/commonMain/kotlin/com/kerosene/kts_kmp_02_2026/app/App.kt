package com.kerosene.kts_kmp_02_2026.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kerosene.core.designsystem.theme.AppTheme
import com.kerosene.kts_kmp_02_2026.navigation.AppContent

@Composable
fun App() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppContent()
        }
    }
}