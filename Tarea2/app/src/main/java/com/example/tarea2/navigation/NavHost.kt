package com.example.tarea2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tarea2.ui.screens.HomeScreen
import com.example.tarea2.ui.screens.OtherScreen

@Composable
fun MyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = "home"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(onNavigate = { navController.navigate("other_view") })
        }
        composable("other_view") {
            OtherScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}