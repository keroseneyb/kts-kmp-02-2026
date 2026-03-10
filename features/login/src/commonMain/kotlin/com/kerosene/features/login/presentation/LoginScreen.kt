package com.kerosene.features.login.presentation

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.kerosene.features.login.presentation.components.LoginWebView
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginUiEvent.LoginSuccessEvent -> onLoginSuccess()
            }
        }
    }

    LoginWebView(
        onLoginSuccess = viewModel::onLoginSuccess,
        modifier = Modifier.systemBarsPadding()
    )
}
