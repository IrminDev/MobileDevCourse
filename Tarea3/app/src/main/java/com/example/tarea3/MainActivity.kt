package com.example.tarea3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.navigation.NavController
import com.example.tarea3.navigation.AppNavigation
import com.example.tarea3.navigation.AppRoutes
import com.example.tarea3.ui.components.FullScreenImageWithGradient
import com.example.tarea3.ui.components.InfoCard
import com.example.tarea3.ui.components.PagerIndicator
import com.example.tarea3.ui.theme.Tarea3Theme
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tarea3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
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
fun ArtGalleryIndexScreen(navController: NavController) {
    val categories = remember {
        listOf(
            ArtCategory("Paintings", "Explore timeless masterpieces...", R.drawable.paintings_bg, AppRoutes.Paintings.route),
            ArtCategory("Murals", "Discover vibrant stories on city walls.", R.drawable.murals_bg, AppRoutes.Murals.route),
            ArtCategory("Sculptures", "Witness form in three dimensions.", R.drawable.sculptures_bg, AppRoutes.Sculptures.route)
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

        PagerIndicator(
            pageCount = pagerState.pageCount,
            currentPage = pagerState.currentPage,
            modifier = Modifier.align(Alignment.BottomCenter)
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
        InfoCard(
            title = category.title,
            description = category.description,
            buttonText = "EXPLORE",
            onButtonClick = onExploreClicked,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}