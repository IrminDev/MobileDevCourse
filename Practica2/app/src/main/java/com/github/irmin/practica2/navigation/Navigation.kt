package com.github.irmin.practica2.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.github.irmin.practica2.ui.screens.FrameworksScreen
import com.github.irmin.practica2.ui.screens.MainScreen

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Frameworks : Screen("frameworks")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(
    navController: NavHostController,
    themeChangeHandler: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(
            route = Screen.Main.route,
        ) {
            MainScreen(
                onNavigateToFrameworks = {
                    navController.navigate(Screen.Frameworks.route)
                },
                themeChangeHandler = themeChangeHandler
            )
        }
        composable(
            route = Screen.Frameworks.route,
        ) {
            FrameworksScreen(onBack = { navController.popBackStack() })
        }
    }
}