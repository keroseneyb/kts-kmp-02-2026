package com.kerosene.features.login.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState
import kotlinx.coroutines.flow.filterNotNull

@Composable
fun LoginWebView(
    onLoginSuccess: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberWebViewState("https://auth.smartbotpro.ru/auth/login")
    val navigator = rememberWebViewNavigator()

    LaunchedEffect(state) {
        snapshotFlow { state.lastLoadedUrl }
            .filterNotNull()
            .collect { currentUrl ->
                val cookies = state.cookieManager.getCookies(currentUrl)
                val sproSessionCookie = cookies.find { it.name == "spro_session" }
                if (sproSessionCookie != null) {
                    onLoginSuccess("spro_session=${sproSessionCookie.value}")
                }
            }
    }

    WebView(
        state = state,
        modifier = modifier.fillMaxSize(),
        navigator = navigator
    )
}
