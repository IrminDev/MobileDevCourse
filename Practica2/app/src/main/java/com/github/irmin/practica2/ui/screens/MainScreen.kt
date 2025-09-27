package com.github.irmin.practica2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.irmin.practica2.R

// Language data model
data class Language(
    val name: String,
    val description: String,
    val logo: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onNavigateToFrameworks: () -> Unit,
               themeChangeHandler: (newTheme: String) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lenguajes de Programación") },
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
            Text("Seleccionar Tema:", style = MaterialTheme.typography.titleMedium)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { themeChangeHandler("Kotlin") }) {
                    Text("Kotlin")
                }
                Button(onClick = { themeChangeHandler("Java") }) {
                    Text("Java")
                }
                Button(onClick = { themeChangeHandler("Python") }) {
                    Text("Python")
                }
                Button(onClick = { themeChangeHandler("C++") }) {
                    Text("C++")
                }
                Button(onClick = { themeChangeHandler("Warm") }) {
                    Text("Warm")
                }
                Button(onClick = { themeChangeHandler("Default") }) {
                    Text("Default")
                }
            }

            val languages = listOf(
                Language("Kotlin", "A modern programming language for Android and JVM.", R.drawable.kotlin_logo),
                Language("Java", "A versatile and widely-used programming language.", R.drawable.java_logo),
                Language("Python", "A powerful language for scripting and data analysis.", R.drawable.python_logo),
                Language("C++", "A high-performance language for system programming.", R.drawable.cpp_logo)
            )

            languages.forEach { language ->
                LanguageCard(language)
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onNavigateToFrameworks) {
                Text("Ver Frameworks")
            }
        }
    }
}

@Composable
fun LanguageCard(language: Language) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = language.logo),
                contentDescription = language.name,
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
                    text = language.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = language.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
