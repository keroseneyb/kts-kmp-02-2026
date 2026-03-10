package com.kerosene.features.feed.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ConversationDetailScreen(
    conversationId: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onBackClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Продолжение следует...",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
