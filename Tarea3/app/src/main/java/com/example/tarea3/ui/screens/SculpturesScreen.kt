package com.example.tarea3.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.tarea3.ui.components.ThemeToggleButton
import com.example.tarea3.ui.theme.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SculpturesScreen(
    navController: NavController,
    themeViewModel: ThemeViewModel = viewModel()
) {
    val isDarkMode by themeViewModel.isDarkMode.collectAsState()
    val sculptures = remember { ArtRepository.getArtPieces(ArtCategory.SCULPTURE) }

    // If there are no sculptures, show a styled empty state
    if (sculptures.isEmpty()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Esculturas",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                "Volver",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                MaterialTheme.colorScheme.background
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No hay esculturas disponibles en este momento.",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }
        }
        return
    }

    val pagerState = rememberPagerState(pageCount = { sculptures.size })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Esculturas",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
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
                val sculptureArtPiece = sculptures[page]
                SculpturePage(
                    artPiece = sculptureArtPiece,
                    navController = navController
                )
            }

            // Animated pager indicator
            AnimatedVisibility(
                visible = sculptures.size > 1,
                modifier = Modifier.align(Alignment.BottomCenter),
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(500)
                ) + fadeIn(tween(500)),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(300)
                ) + fadeOut(tween(300))
            ) {
                PagerIndicator(
                    pageCount = pagerState.pageCount,
                    currentPage = pagerState.currentPage
                )
            }

            // Theme toggle button in the top right
            ThemeToggleButton(
                isDarkMode = isDarkMode,
                onToggle = { themeViewModel.toggleTheme() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .statusBarsPadding()
            )
        }
    }
}

@Composable
fun SculpturePage(artPiece: ArtPiece, navController: NavController) {
    FullScreenImageWithGradient(imageRes = artPiece.imageRes) {
        AnimatedVisibility(
            visible = true,
            modifier = Modifier.align(Alignment.BottomStart),
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(800)
            ) + fadeIn(tween(800))
        ) {
            InfoCard(
                title = artPiece.title,
                description = artPiece.shortDescription,
                buttonText = "VER DETALLES",
                onButtonClick = {
                    navController.navigate(AppRoutes.ArtDetail.createRoute(
                        artPiece.id
                    ))
                }
            )
        }
    }
}
