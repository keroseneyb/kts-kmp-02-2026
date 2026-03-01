package com.kerosene.features.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerosene.core.common.handleApiCall
import com.kerosene.features.auth.domain.repository.LoginRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        LoginUiState(
            username = "",
            password = "",
            isLoginButtonActive = false,
            error = ""
        )
    )
    val state: StateFlow<LoginUiState> = _state

    private val _events = MutableSharedFlow<LoginUiEvent>()
    val events = _events.asSharedFlow()

    fun onUsernameChanged(userName: String) {
        _state.update { uiModel ->
            uiModel.copy(
                username = userName,
                isLoginButtonActive = isInputValid(userName, uiModel.password)
            )
        }
    }

    fun onPasswordChanged(password: String) {
        _state.update { uiModel ->
            uiModel.copy(
                password = password,
                isLoginButtonActive = isInputValid(uiModel.username, password)
            )
        }
    }

    private fun isInputValid(username: String, password: String): Boolean {
        return username.isNotBlank() && password.isNotBlank()
    }

    fun onLoginClick() {
        val currentState = _state.value
        viewModelScope.launch {
            handleApiCall(
                apiCall = { loginRepository.login(currentState.username, currentState.password) },
                onSuccess = {
                    _events.emit(LoginUiEvent.LoginSuccessEvent)
                },
                onError = { message ->
                    _state.update { it.copy(error = message) }
                }
            )
        }
    }
}
