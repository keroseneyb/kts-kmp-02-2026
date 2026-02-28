package com.kerosene.kts_kmp_02_2026.feature.login.presentation

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kerosene.kts_kmp_02_2026.common.ui.AppImage
import com.kerosene.kts_kmp_02_2026.feature.login.presentation.Dimens.elementsSpacer
import ktskmp022026.composeapp.generated.resources.Res
import ktskmp022026.composeapp.generated.resources.email
import ktskmp022026.composeapp.generated.resources.login
import ktskmp022026.composeapp.generated.resources.password
import org.jetbrains.compose.resources.stringResource

object Dimens {
    val elementsSpacer = 24.dp
}

@Composable
fun LoginScreen() {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
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
        EmailTextInput()
        Spacer(modifier = Modifier.height(elementsSpacer))
        PasswordTextInput()
        Spacer(modifier = Modifier.height(elementsSpacer))
        LoginButton()
    }
}

@Composable
private fun LoginImage() {
    AppImage(
        model = "https://i.pinimg.com/736x/3f/94/70/3f9470b34a8e3f526dbdb022f9f19cf7.jpg"
    )
}

@Composable
private fun EmailTextInput() {
    var email by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text(text = stringResource(Res.string.email)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        )
    )
}

@Composable
private fun PasswordTextInput() {
    var password by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text = stringResource(Res.string.password)) },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        )
    )
}

@Composable
private fun LoginButton() {
    Button(
        onClick = {},
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = stringResource(Res.string.login))
    }
}

@Preview
@Composable
private fun TestLoginScreen() {
    LoginScreen()
}