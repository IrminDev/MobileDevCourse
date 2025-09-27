package com.github.irmin.practica2.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// Define themes for Kotlin, Java, Python, and C++
private val KotlinColorScheme = lightColorScheme(
    primary = KotlinPrimary,
    onPrimary = KotlinOnPrimary,
    secondary = PurpleGrey40,
    onSecondary = KotlinOnPrimary,
    tertiary = Pink40,
    onTertiary = KotlinOnPrimary
)

private val JavaColorScheme = lightColorScheme(
    primary = JavaPrimary,
    onPrimary = JavaOnPrimary,
    secondary = PurpleGrey40,
    onSecondary = JavaOnPrimary,
    tertiary = Pink40,
    onTertiary = JavaOnPrimary
)

private val PythonColorScheme = lightColorScheme(
    primary = PythonPrimary,
    onPrimary = PythonOnPrimary,
    secondary = PurpleGrey40,
    onSecondary = PythonOnPrimary,
    tertiary = Pink40,
    onTertiary = PythonOnPrimary
)

private val CppColorScheme = lightColorScheme(
    primary = CppPrimary,
    onPrimary = CppOnPrimary,
    secondary = PurpleGrey40,
    onSecondary = CppOnPrimary,
    tertiary = Pink40,
    onTertiary = CppOnPrimary
)

@Composable
fun Practica2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    selectedTheme: String = "Default",
    content: @Composable () -> Unit
) {
    val colorScheme = when (selectedTheme) {
        "Kotlin" -> KotlinColorScheme
        "Java" -> JavaColorScheme
        "Python" -> PythonColorScheme
        "C++" -> CppColorScheme
        else -> if (darkTheme) DarkColorScheme else LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}