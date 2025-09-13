package com.example.tarea3.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A Composable that displays an image with a parallax scrolling effect.
 *
 * @param imageRes The drawable resource ID for the image to display.
 * @param headerHeight The total height of the header area.
 * @param scrollOffsetPx The current scroll offset in pixels. This value is typically
 *                       derived from a NestedScrollConnection in the parent Composable.
 *                       A positive value usually means scrolling down (content moving up).
 * @param modifier Modifier for this Composable.
 */
@Composable
fun ParallaxImageHeader(
    @DrawableRes imageRes: Int,
    headerHeight: Dp = 300.dp,
    scrollOffsetPx: Float,
    modifier: Modifier = Modifier
) {
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(headerHeight) // Set the fixed height for the header area
            .graphicsLayer {
                // Apply parallax effect: Move the image slower than the scroll.
                // The factor (e.g., 0.5f) determines the parallax intensity.
                // A smaller factor means a more pronounced parallax effect (image moves less).
                translationY = scrollOffsetPx * 0.5f

                // Optional: Control alpha (fade) based on how much of the header is scrolled off-screen.
                // This calculates how much of the header is "gone" from the top.
                val visibleFraction = (headerHeightPx + scrollOffsetPx) / headerHeightPx
                alpha = visibleFraction.coerceIn(0f, 1f)

                // Optional: Add a subtle zoom out effect as it scrolls.
                // val scale = 1f + (scrollOffsetPx / headerHeightPx) * 0.1f // Small zoom
                // scaleX = scale.coerceAtLeast(1f)
                // scaleY = scale.coerceAtLeast(1f)
            }
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null, // Content description should be meaningful if the image is conveying info
            contentScale = ContentScale.Crop, // Crop ensures the image fills the bounds
            modifier = Modifier.fillMaxSize()
        )
    }
}
