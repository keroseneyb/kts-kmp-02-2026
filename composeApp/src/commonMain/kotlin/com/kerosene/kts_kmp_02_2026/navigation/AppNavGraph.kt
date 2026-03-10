package com.kerosene.kts_kmp_02_2026.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kerosene.core.auth.domain.repository.AuthRepository
import com.kerosene.core.navigation.Feed
import com.kerosene.core.navigation.Greeting
import com.kerosene.core.navigation.Login
import com.kerosene.core.navigation.ConversationDetail
import com.kerosene.features.login.navigation.loginScreen
import com.kerosene.features.feed.api.FeedNavigationApi
import com.kerosene.features.greeting.navigation.greetingScreen
import org.koin.compose.koinInject

@Composable
fun AppNavGraph(
    navController: NavHostController,
    authRepository: AuthRepository = koinInject(),
    feedNavigationApi: FeedNavigationApi = koinInject()
) {
    val sessionCookie by authRepository.getSessionCookie().collectAsState(initial = "")

    LaunchedEffect(sessionCookie) {
        if (sessionCookie == null) {
            navController.navigate(Greeting) {
                popUpTo(0) { inclusive = true }
            }
        } else if (sessionCookie!!.isNotEmpty()) {
            navController.navigate(Feed) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Greeting
    ) {
        greetingScreen(
            onGoToLoginButtonClick = {
                navController.navigate(route = Login)
            }
        )
        loginScreen(
            onLoginSuccess = {
                navController.navigate(route = Feed) {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
            }
        )
        feedNavigationApi.register(
            navGraphBuilder = this,
            onChatClick = { conversationId ->
                navController.navigate(route = ConversationDetail(conversationId))
            },
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}
