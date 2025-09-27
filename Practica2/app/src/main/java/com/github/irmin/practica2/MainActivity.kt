package com.github.irmin.practica2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.github.irmin.practica2.navigation.AppNavigation
import com.github.irmin.practica2.ui.theme.Practica2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var theme by remember { mutableStateOf("Java") }

            Practica2Theme(selectedTheme = theme){
                val navController = rememberNavController()
                val themeChangeHandler: (String) -> Unit = { newTheme ->
                    theme = newTheme
                }
                AppNavigation(
                    navController = navController,
                    themeChangeHandler = themeChangeHandler)
            }
        }
    }
}