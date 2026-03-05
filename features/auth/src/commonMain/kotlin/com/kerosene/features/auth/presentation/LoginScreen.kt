package com.kerosene.features.auth.presentation

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.kerosene.core.common.ui.AppImage
import com.kerosene.features.auth.Res
import com.kerosene.features.auth.email
import com.kerosene.features.auth.login
import com.kerosene.features.auth.password
import com.kerosene.features.auth.presentation.Dimens.elementsSpacer
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

object Dimens {
    val elementsSpacer = 24.dp
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginUiEvent.LoginSuccessEvent -> onLoginSuccess()
            }
        }
    }

    LoginContent(
        username = state.username,
        password = state.password,
        isLoginButtonActive = state.isLoginButtonActive,
        error = state.error,
        onUsernameChanged = viewModel::onUsernameChanged,
        onPasswordChanged = viewModel::onPasswordChanged,
        onLoginClick = viewModel::onLoginClick,
        scrollState = scrollState,
        focusManager = focusManager,
        keyboardController = keyboardController
    )
}

@Composable
private fun LoginContent(
    username: String,
    password: String,
    isLoginButtonActive: Boolean,
    error: String?,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
    scrollState: ScrollState,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .verticalScroll(scrollState)
            .imePadding()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
                keyboardController?.hide()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoginImage()
        Spacer(modifier = Modifier.height(elementsSpacer))

        EmailTextInput(
            value = username,
            onValueChange = onUsernameChanged
        )

        Spacer(modifier = Modifier.height(elementsSpacer))

        PasswordTextInput(
            value = password,
            onValueChange = onPasswordChanged
        )

        LoginError(message = error)

        Spacer(modifier = Modifier.height(elementsSpacer))

        LoginButton(
            enabled = isLoginButtonActive,
            onClick = onLoginClick
        )
    }
}

@Composable
private fun LoginImage() {
    AppImage(
        model = "https://i.pinimg.com/736x/3f/94/70/3f9470b34a8e3f526dbdb022f9f19cf7.jpg"
    )
}

@Composable
private fun EmailTextInput(
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(Res.string.email)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        )
    )
}

@Composable
private fun PasswordTextInput(
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = stringResource(Res.string.password)) },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        )
    )
}

@Composable
private fun LoginButton(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = stringResource(Res.string.login))
    }
}

@Composable
private fun LoginError(message: String?) {
    if (message == null) return

    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error
    )
}