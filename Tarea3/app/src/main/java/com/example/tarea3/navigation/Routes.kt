package com.example.tarea3.navigation

sealed class AppRoutes(val route: String) {
    object Index : AppRoutes("index_screen")
    object Paintings : AppRoutes("paintings_screen")
    object Murals : AppRoutes("murals_screen")
    object Sculptures : AppRoutes("sculptures_screen")
    object ArtDetail : AppRoutes("art_detail/{artId}") { // {artId} is the argument placeholder
        fun createRoute(artId: String) = "art_detail/$artId"
    }
}