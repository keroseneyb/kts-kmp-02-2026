package com.kerosene.features.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kerosene.core.common.navigation.Login
import com.kerosene.features.auth.presentation.LoginScreen

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit
) {
    composable<Login> {
        LoginScreen(onLoginSuccess = onLoginSuccess)
    }
}
