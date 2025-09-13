package com.example.tarea3.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A reusable card for displaying a title, description, and an action button.
 * Designed to be placed on top of a background.
 *
 * @param title The main title text.
 * @param description The descriptive text below the title.
 * @param buttonText The text for the action button.
 * @param onButtonClick The lambda to be executed when the button is clicked.
 * @param modifier The modifier to be applied to the component.
 */
@Composable
fun InfoCard(
    title: String,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 80.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 1.2.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedButton(
            onClick = onButtonClick,
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
            border = BorderStroke(1.dp, Color.White)
        ) {
            Text(buttonText, fontWeight = FontWeight.SemiBold)
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}