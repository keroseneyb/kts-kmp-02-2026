package com.kerosene.features.auth.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val username: String,
    val password: String,
    val isLoading: Boolean,
    val error: String?,
) {
    val isLoginButtonActive: Boolean
        get() = username.isNotBlank() && password.isNotBlank() && !isLoading
}