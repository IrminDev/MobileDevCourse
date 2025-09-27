package com.example.tarea3.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A floating action button for toggling between light and dark themes
 * with smooth rotation animation.
 *
 * @param isDarkMode Current theme state
 * @param onToggle Callback when theme should be toggled
 * @param modifier The modifier to be applied to the component
 */
@Composable
fun ThemeToggleButton(
    isDarkMode: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val rotation by animateFloatAsState(
        targetValue = if (isDarkMode) 180f else 0f,
        animationSpec = tween(500),
        label = "theme_toggle_rotation"
    )

    FloatingActionButton(
        onClick = onToggle,
        modifier = modifier.size(56.dp),
        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f),
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Icon(
            imageVector = if (isDarkMode) Icons.Default.Star else Icons.Default.Favorite,
            contentDescription = if (isDarkMode) "Switch to Light Mode" else "Switch to Dark Mode",
            modifier = Modifier.rotate(rotation),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
