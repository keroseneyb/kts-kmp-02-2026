package com.kerosene.kts_kmp_02_2026.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kerosene.kts_kmp_02_2026.feature.greeting.presentation.GreetingScreen
import com.kerosene.kts_kmp_02_2026.feature.login.presentation.LoginScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Greeting
    ) {
        composable<Greeting> {
            GreetingScreen(
                onGoToLoginButtonClick = {
                    navController.navigate(route = Login)
                }
            )
        }
        composable<Login> {
            LoginScreen()
        }
    }
}