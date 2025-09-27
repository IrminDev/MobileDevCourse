package com.github.irmin.practica2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// Removed: import androidx.compose.ui.graphics.Color // No longer needed for local themes
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.irmin.practica2.R
import com.github.irmin.practica2.ui.theme.Practica2Theme


data class Framework(
    val name: String,
    val description: String,
    val logo: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrameworksScreen(onBack: () -> Unit) {
    val frameworks = listOf(
        Framework("Spring", "A powerful framework for building Java applications.", R.drawable.spring_logo),
        Framework("Django", "A high-level Python web framework.", R.drawable.django_logo),
        Framework("React", "A JavaScript library for building user interfaces.", R.drawable.react_logo),
        Framework("Angular", "A platform for building mobile and desktop web applications.", R.drawable.angular_logo)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Frameworks de Programación") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
                // TopAppBar will use colors from Practica2Theme (e.g., MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            frameworks.forEach { framework ->
                FrameworkCard(framework) // MODIFIED: Removed selectedTheme parameter
            }
        }
    }
}

@Composable
fun FrameworkCard(framework: Framework) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = framework.logo),
                contentDescription = framework.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = framework.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    // MODIFIED: Use color from MaterialTheme
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = framework.description,
                    fontSize = 14.sp,
                    // MODIFIED: Use color from MaterialTheme
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
