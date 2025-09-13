package com.example.tarea3.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

/**
 * A reusable component that displays a full-screen image with a vertical gradient overlay.
 * The content lambda allows placing other composables on top.
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
    Box(modifier = modifier.fillMaxSize()) {
        // Background Image
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null, // Decorative image
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay for text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = 600f // Start gradient lower on the screen
                    )
                )
        )

        // The content is placed here, on top of the image and gradient
        content()
    }
}