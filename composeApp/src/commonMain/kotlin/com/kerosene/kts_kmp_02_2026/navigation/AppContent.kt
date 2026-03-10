package com.kerosene.kts_kmp_02_2026.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppContent() {
    val navController = rememberNavController()
    AppNavGraph(navController = navController)
}