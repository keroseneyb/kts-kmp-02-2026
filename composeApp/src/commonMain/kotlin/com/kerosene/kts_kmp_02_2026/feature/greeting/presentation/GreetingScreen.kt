package com.kerosene.kts_kmp_02_2026.feature.greeting.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ktskmp022026.composeapp.generated.resources.Res
import ktskmp022026.composeapp.generated.resources.gotologin
import ktskmp022026.composeapp.generated.resources.welcome
import org.jetbrains.compose.resources.stringResource

@Composable
fun GreetingScreen(
    onGoToLoginButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GreetingImage()
        Spacer(modifier = Modifier.height(24.dp))
        GreetingText()
        Spacer(modifier = Modifier.height(24.dp))
        GoToLoginScreenButton(onGoToLoginButtonClick = onGoToLoginButtonClick)
    }
}

@Composable
private fun GreetingImage() {
    AsyncImage(
        model = "https://i.pinimg.com/736x/4e/5c/f7/4e5cf7d4ccb9c59b6620a9c71944d51e.jpg",
        contentScale = ContentScale.Fit,
        modifier = Modifier.fillMaxWidth(),
        contentDescription = null
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