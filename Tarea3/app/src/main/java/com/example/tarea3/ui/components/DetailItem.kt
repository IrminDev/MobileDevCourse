package com.example.tarea3.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A Composable that displays a label and its corresponding value.
 * Useful for showing details in a list-like format.
 *
 * @param label The text for the label (e.g., "Artist", "Year").
 * @param value The text for the value associated with the label.
 * @param modifier Modifier for this Composable.
 * @param spacing The vertical spacing between the label and the value.
 */
@Composable
fun DetailItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    spacing: Dp = 2.dp // Small spacing between label and value
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium, // Using a slightly more prominent label style
            color = MaterialTheme.colorScheme.onSurfaceVariant, // Good contrast for a secondary label
            fontSize = 13.sp, // Slightly larger for better readability
            modifier = Modifier.padding(bottom = spacing)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Normal // Standard weight for detail values
        )
    }
}
