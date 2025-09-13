package com.example.tarea3.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember // Keep this import
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
// Import classes from your data layer
import com.example.tarea3.data.ArtCategory
import com.example.tarea3.data.ArtPiece
import com.example.tarea3.data.ArtRepository
// Import navigation routes
import com.example.tarea3.navigation.AppRoutes
// Import your UI components
import com.example.tarea3.ui.components.FullScreenImageWithGradient
import com.example.tarea3.ui.components.InfoCard
import com.example.tarea3.ui.components.PagerIndicator

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PaintingsScreen(navController: NavController) {
    // Fetch paintings data from the ArtRepository
    val paintings = remember { ArtRepository.getArtPieces(ArtCategory.PAINTING) }

    // Optional: Handle empty state
    if (paintings.isEmpty()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Paintings") },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No paintings available at the moment.")
            }
        }
        return
    }

    val pagerState = rememberPagerState(pageCount = { paintings.size })

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Paintings") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val paintingArtPiece = paintings[page] // This is now an ArtPiece
                PaintingPage(
                    artPiece = paintingArtPiece,
                    navController = navController // Pass NavController for navigation
                )
            }

            PagerIndicator(
                pageCount = pagerState.pageCount,
                currentPage = pagerState.currentPage,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Composable
fun PaintingPage(artPiece: ArtPiece, navController: NavController) { // Now accepts ArtPiece and NavController
    FullScreenImageWithGradient(imageRes = artPiece.imageRes) {
        InfoCard(
            title = artPiece.title,
            // Use shortDescription for the preview in the gallery
            description = artPiece.shortDescription,
            buttonText = "VIEW DETAILS", // Or "LEARN MORE"
            onButtonClick = {
                // Navigate to the ArtDetail screen using the artPiece's id
                navController.navigate(AppRoutes.ArtDetail.createRoute(artPiece.id))
            },
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}
