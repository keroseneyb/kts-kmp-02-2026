package com.kerosene.features.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerosene.core.auth.api.TokenProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val tokenProvider: TokenProvider,
) : ViewModel() {

    private val _events = MutableSharedFlow<LoginUiEvent>()
    val events = _events.asSharedFlow()

    fun onLoginSuccess(cookie: String) {
        viewModelScope.launch {
            tokenProvider.saveTokens(cookie, "")
            _events.emit(LoginUiEvent.LoginSuccessEvent)
        }
    }
}
