package com.example.tarea3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tarea3.navigation.AppNavigation
import com.example.tarea3.navigation.AppRoutes
import com.example.tarea3.ui.components.FullScreenImageWithGradient
import com.example.tarea3.ui.components.InfoCard
import com.example.tarea3.ui.components.PagerIndicator
import com.example.tarea3.ui.components.ThemeToggleButton
import com.example.tarea3.ui.theme.Tarea3Theme
import com.example.tarea3.ui.theme.ThemeViewModel
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by themeViewModel.isDarkMode.collectAsState()

            Tarea3Theme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        onToggleTheme = { themeViewModel.toggleTheme() },
                        isDarkMode = isDarkMode
                    )
                }
            }
        }
    }
}

data class ArtCategory(
    val title: String,
    val description: String,
    val imageRes: Int,
    val route: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtGalleryIndexScreen(navController: NavController,
    themeViewModel: ThemeViewModel = viewModel(), ) {
    val categories = remember {
        listOf(
            ArtCategory(
                "Pinturas",
                "Explora obras maestras atemporales que capturan la esencia del arte en lienzo.",
                R.drawable.paintings_bg,
                AppRoutes.Paintings.route
            ),
            ArtCategory(
                "Murales",
                "Descubre historias vibrantes narradas en las paredes de la ciudad.",
                R.drawable.murals_bg,
                AppRoutes.Murals.route
            ),
            ArtCategory(
                "Esculturas",
                "Contempla la forma y la expresión en tres dimensiones.",
                R.drawable.sculptures_bg,
                AppRoutes.Sculptures.route
            )
        )
    }

    val pagerState = rememberPagerState(pageCount = { categories.size })

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val category = categories[page]
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
            val imageAlpha = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
            val imageScale = 1f + (pageOffset.absoluteValue * 0.2f)

            ArtCategoryPage(
                category = category,
                onExploreClicked = { navController.navigate(category.route) },
                modifier = Modifier.graphicsLayer {
                    translationX = pageOffset * (size.width / 2)
                    alpha = imageAlpha
                    scaleX = imageScale
                    scaleY = imageScale
                }
            )
        }

        // Animated pager indicator
        AnimatedVisibility(
            visible = categories.size > 1,
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

        ThemeToggleButton(
            isDarkMode = themeViewModel.isDarkMode.collectAsState().value,
            onToggle = { themeViewModel.toggleTheme() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .statusBarsPadding()
        )
    }
}

@Composable
fun ArtCategoryPage(
    category: ArtCategory,
    onExploreClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    FullScreenImageWithGradient(imageRes = category.imageRes, modifier = modifier) {
        AnimatedVisibility(
            visible = true,
            modifier = Modifier.align(Alignment.BottomStart),
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(800)
            ) + fadeIn(tween(800))
        ) {
            InfoCard(
                title = category.title,
                description = category.description,
                buttonText = "EXPLORAR",
                onButtonClick = onExploreClicked
            )
        }
    }
}