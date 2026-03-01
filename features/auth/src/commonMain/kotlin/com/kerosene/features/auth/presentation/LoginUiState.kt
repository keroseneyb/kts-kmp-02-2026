package com.kerosene.features.auth.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val username: String,
    val password: String,
    val isLoginButtonActive: Boolean,
    val error: String?,
)
