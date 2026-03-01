package com.kerosene.features.greeting.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kerosene.core.common.ui.AppImage
import com.kerosene.features.greeting.Res
import com.kerosene.features.greeting.gotologin
import com.kerosene.features.greeting.presentation.Dimens.elementsSpacer
import com.kerosene.features.greeting.welcome
import org.jetbrains.compose.resources.stringResource

object Dimens {
    val elementsSpacer = 24.dp
}

@Composable
fun GreetingScreen(
    onGoToLoginButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GreetingImage()
        Spacer(modifier = Modifier.height(elementsSpacer))
        GreetingText()
        Spacer(modifier = Modifier.height(elementsSpacer))
        GoToLoginScreenButton(onGoToLoginButtonClick = onGoToLoginButtonClick)
    }
}

@Composable
private fun GreetingImage() {
    AppImage(
        model = "https://i.pinimg.com/736x/4e/5c/f7/4e5cf7d4ccb9c59b6620a9c71944d51e.jpg",
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun GreetingText() {
    Text(text = stringResource(Res.string.welcome))
}

@Composable
private fun GoToLoginScreenButton(
    onGoToLoginButtonClick: () -> Unit
) {
    Button(
        onClick = onGoToLoginButtonClick,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = stringResource(Res.string.gotologin))
    }
}

@Preview
@Composable
fun TestGreetingScreen() {
    GreetingScreen(onGoToLoginButtonClick = {})
}