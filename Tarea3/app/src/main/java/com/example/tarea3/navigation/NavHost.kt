package com.example.tarea3.navigation

import androidx.compose.material3.Text // Added for placeholder error
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tarea3.ArtGalleryIndexScreen
import com.example.tarea3.data.ArtRepository // Essential for fetching data
import com.example.tarea3.ui.screens.ArtDetailScreen // The new detail screen
import com.example.tarea3.ui.screens.MuralsScreen
import com.example.tarea3.ui.screens.PaintingsScreen
import com.example.tarea3.ui.screens.SculpturesScreen

@Composable
fun AppNavigation() {
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

        // Corrected and updated ArtDetail composable
        composable(
            route = AppRoutes.ArtDetail.route, // This should be "art_detail/{artId}"
            arguments = listOf(navArgument("artId") {
                type = NavType.StringType
                nullable = false // artId should not be null
            })
        ) { backStackEntry ->
            // Retrieve the artId argument
            val artId = backStackEntry.arguments?.getString("artId")

            // It's crucial that artId is not null here.
            // If it can be, you'd need more robust error handling or make it non-nullable in the route.
            if (artId != null) {
                // Find the art piece from your repository using the artId
                val artPiece = ArtRepository.findArtPieceById(artId)

                if (artPiece != null) {
                    // Pass the fetched artPiece to the new ArtDetailScreen
                    ArtDetailScreen(artPiece = artPiece, navController = navController)
                } else {
                    // Handle the case where the artId is valid but no art piece is found
                    // This could be a Text composable or navigate back
                    Text("Error: Art piece with ID '$artId' not found.")
                    // Consider navController.popBackStack() or navigating to an error screen
                }
            } else {
                // Handle the case where artId is null (shouldn't happen if argument is non-nullable)
                Text("Error: Art ID is missing in navigation.")
                // Consider navController.popBackStack()
            }
        }
    }
}

