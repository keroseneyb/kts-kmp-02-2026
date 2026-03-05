package com.kerosene.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kerosene.core.common.navigation.Feed
import com.kerosene.core.common.navigation.Greeting
import com.kerosene.core.common.navigation.Login
import com.kerosene.features.auth.navigation.loginScreen
import com.kerosene.features.feed.navigation.feedScreen
import com.kerosene.features.greeting.navigation.greetingScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Greeting
    ) {
        greetingScreen(
            onGoToLoginButtonClick = {
                navController.navigate(route = Login)
            }
        )
        loginScreen(
            onLoginSuccess = {
                navController.navigate(route = Feed) {
                    popUpTo(Greeting) {
                        inclusive = true
                    }
                }
            }
        )
        feedScreen()
    }
}
