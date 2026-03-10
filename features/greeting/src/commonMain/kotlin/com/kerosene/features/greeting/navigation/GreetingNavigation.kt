package com.kerosene.features.greeting.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kerosene.core.navigation.Greeting
import com.kerosene.features.greeting.presentation.GreetingScreen

fun NavGraphBuilder.greetingScreen(
    onGoToLoginButtonClick: () -> Unit
) {
    composable<Greeting> {
        GreetingScreen(onGoToLoginButtonClick = onGoToLoginButtonClick)
    }
}
