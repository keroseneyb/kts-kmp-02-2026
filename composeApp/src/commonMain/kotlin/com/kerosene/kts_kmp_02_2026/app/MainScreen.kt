package com.kerosene.kts_kmp_02_2026.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.kerosene.kts_kmp_02_2026.core.navigation.AppNavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    AppNavGraph(navController = navController)
}