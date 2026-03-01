package com.kerosene.kts_kmp_02_2026.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.kerosene.core.navigation.AppNavGraph

@Composable
fun AppContent() {
    val navController = rememberNavController()
    AppNavGraph(navController = navController)
}