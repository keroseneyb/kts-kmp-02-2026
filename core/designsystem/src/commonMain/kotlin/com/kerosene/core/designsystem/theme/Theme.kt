package com.kerosene.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.kerosene.core.designsystem.ui.AppDimens

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    secondary = MutedPrimaryDark,
    onSecondary = TextDark,
    background = BackgroundDark,
    onBackground = TextDark,
    surface = BackgroundDark,
    onSurface = TextDark,
    surfaceVariant = BackgroundDark,
    onSurfaceVariant = TextDark.copy(alpha = 0.7f),
    outline = MutedPrimaryDark,
    outlineVariant = MutedPrimaryDark.copy(alpha = 0.5f)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    secondary = MutedPrimaryLight,
    onSecondary = TextLight,
    background = BackgroundLight,
    onBackground = TextLight,
    surface = BackgroundLight,
    onSurface = TextLight,
    surfaceVariant = MutedPrimaryLight.copy(alpha = 0.2f),
    onSurfaceVariant = TextLight.copy(alpha = 0.7f),
    outline = MutedPrimaryLight,
    outlineVariant = MutedPrimaryLight.copy(alpha = 0.5f)
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
            typography = appTypography(isNight = darkTheme),
            shapes = AppShapes,
            content = content
        )
    }

