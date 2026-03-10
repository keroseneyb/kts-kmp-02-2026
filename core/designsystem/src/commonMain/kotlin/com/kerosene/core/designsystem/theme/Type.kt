package com.kerosene.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

fun appTypography(isNight: Boolean): Typography {
    return Typography(
        displayLarge = TextStyle(
            fontSize = 57.sp,
            lineHeight = 64.sp,
            fontWeight = FontWeight.Medium
        ),
        displayMedium = TextStyle(
            fontSize = 45.sp,
            lineHeight = 52.sp,
            fontWeight = FontWeight.Medium
        ),
        headlineLarge = TextStyle(
            fontSize = 32.sp,
            lineHeight = 40.sp,
            fontWeight = FontWeight.Medium
        ),
        headlineSmall = TextStyle(
            fontSize = 24.sp,
            lineHeight = 32.sp,
            fontWeight = FontWeight.Medium
        ),
        titleLarge = TextStyle(
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Medium
        ),
        bodyLarge = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Normal
        ),
        bodyMedium = TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal
        ),
        labelLarge = TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Medium
        ),
        bodySmall = TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Normal
        )
    )
}
