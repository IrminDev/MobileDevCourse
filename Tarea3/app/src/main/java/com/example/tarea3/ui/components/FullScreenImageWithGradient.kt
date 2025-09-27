package com.example.tarea3.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

/**
 * A reusable component that displays a full-screen image with a themed gradient overlay.
 * The content lambda allows placing other composables on top.
 * The gradient adapts to the current theme.
 *
 * @param imageRes The drawable resource ID for the background image.
 * @param modifier The modifier to be applied to the component.
 * @param content The composable content to be displayed on top of the image and gradient.
 */
@Composable
fun FullScreenImageWithGradient(
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val surfaceColor = MaterialTheme.colorScheme.surface

    Box(modifier = modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null, // Decorative image
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay for text readability - adapts to theme
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            surfaceColor.copy(alpha = 0.3f),
                            surfaceColor.copy(alpha = 0.85f)
                        ),
                        startY = 400f,
                        endY = 1200f
                    )
                )
        )

        // The content is placed here, on top of the image and gradient
        content()
    }
}