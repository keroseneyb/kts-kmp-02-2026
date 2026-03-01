package com.kerosene.features.auth.presentation

sealed interface LoginUiEvent {
    data object LoginSuccessEvent : LoginUiEvent
}
