package com.example.tarea3.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.tarea3.components.ArtInfoComponent
import com.example.tarea3.model.ArtWork

@Composable
fun ArtInfoScreen(navController: NavController, artWork: ArtWork) {
    ArtInfoComponent(artWork = artWork)
}
