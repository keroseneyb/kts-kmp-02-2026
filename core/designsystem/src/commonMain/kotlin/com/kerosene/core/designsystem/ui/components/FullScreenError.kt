package com.kerosene.core.designsystem.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.kerosene.core.designsystem.ui.AppDimens
import ktskmp022026.core.designsystem.generated.resources.Res
import ktskmp022026.core.designsystem.generated.resources.common_retry
import org.jetbrains.compose.resources.stringResource

@Composable
fun FullScreenError(
    message: String,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null
) {
    Box(
        modifier = modifier.fillMaxSize().padding(AppDimens.large),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            
            if (onRetry != null) {
                Spacer(modifier = Modifier.height(AppDimens.medium))
                Button(onClick = onRetry) {
                    Text(text = stringResource(Res.string.common_retry))
                }
            }
        }
    }
}
