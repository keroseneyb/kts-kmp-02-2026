package com.kerosene.core.designsystem.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import ktskmp022026.core.designsystem.generated.resources.Res
import ktskmp022026.core.designsystem.generated.resources.ic_image_error
import ktskmp022026.core.designsystem.generated.resources.ic_image_placeholder
import org.jetbrains.compose.resources.painterResource

@Composable
fun AppImage(
    model: Any?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
    placeholder: Painter? = painterResource(Res.drawable.ic_image_placeholder),
    error: Painter? = painterResource(Res.drawable.ic_image_error),
) {
    AsyncImage(
        model = model,
        modifier = modifier,
        contentScale = contentScale,
        placeholder = placeholder,
        error = error,
        contentDescription = contentDescription
    )
}
