package com.kerosene.features.login.presentation

sealed interface LoginUiEvent {
    data object LoginSuccessEvent : LoginUiEvent
}
