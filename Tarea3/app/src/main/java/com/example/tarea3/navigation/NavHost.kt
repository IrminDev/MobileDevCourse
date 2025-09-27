package com.example.tarea3.navigation

import androidx.compose.material3.Text // Added for placeholder error
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tarea3.ArtGalleryIndexScreen
import com.example.tarea3.data.ArtRepository
import com.example.tarea3.ui.screens.ArtDetailScreen
import com.example.tarea3.ui.screens.MuralsScreen
import com.example.tarea3.ui.screens.PaintingsScreen
import com.example.tarea3.ui.screens.SculpturesScreen
import com.example.tarea3.ui.theme.ThemeViewModel

@Composable
fun AppNavigation(
    onToggleTheme: () -> Unit,
    isDarkMode: Boolean
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppRoutes.Index.route
    ) {
        composable(route = AppRoutes.Index.route) {
            ArtGalleryIndexScreen(navController = navController)
        }
        composable(route = AppRoutes.Paintings.route) {
            PaintingsScreen(navController = navController)
        }
        composable(route = AppRoutes.Murals.route) {
            MuralsScreen(navController = navController)
        }
        composable(route = AppRoutes.Sculptures.route) {
            SculpturesScreen(navController = navController)
        }

        composable(
            route = AppRoutes.ArtDetail.route,
            arguments = listOf(navArgument("artId") {
                type = NavType.StringType
                nullable = false
            })
        ) { backStackEntry ->
            val artId = backStackEntry.arguments?.getString("artId")

            if (artId != null) {
                val artPiece = ArtRepository.findArtPieceById(artId)

                if (artPiece != null) {
                    ArtDetailScreen(artPiece = artPiece, navController = navController, onToggleTheme = onToggleTheme, isDarkMode = isDarkMode)
                } else {
                    Text("Error: Art piece with ID '$artId' not found.")
                }
            } else {
                Text("Error: Art ID is missing in navigation.")
            }
        }
    }
}

