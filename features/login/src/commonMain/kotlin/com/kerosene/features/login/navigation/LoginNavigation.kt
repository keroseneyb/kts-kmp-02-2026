package com.kerosene.features.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kerosene.core.navigation.Login
import com.kerosene.features.login.presentation.LoginScreen

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit
) {
    composable<Login> {
        LoginScreen(onLoginSuccess = onLoginSuccess)
    }
}
