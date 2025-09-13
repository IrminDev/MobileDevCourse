package com.example.tarea3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.layout.height
import androidx.navigation.NavController
import com.example.tarea3.data.ArtCategory // Import ArtCategory for the 'when' statement
import com.example.tarea3.data.ArtPiece
import com.example.tarea3.ui.components.DetailItem
import com.example.tarea3.ui.components.ParallaxImageHeader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtDetailScreen(
    navController: NavController,
    artPiece: ArtPiece // Expecting the full ArtPiece object
) {
    val lazyListState = rememberLazyListState()

    // Configuration for the parallax header
    val headerHeight = 300.dp // Define the height of the parallax image area
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    // Min height for the header when collapsed (e.g., TopAppBar height)
    val minCollapsedHeaderHeightPx = with(LocalDensity.current) { TopAppBarDefaults.TopAppBarExpandedHeight.toPx() }

    var headerOffsetPx by remember { mutableFloatStateOf(0f) } // Raw pixel offset for the header

    // Nested scroll connection to listen to scroll events
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = headerOffsetPx + delta
                // Coerce the offset:
                // min value: -(headerHeightPx - minCollapsedHeaderHeightPx) -> header fully collapsed
                // max value: 0f -> header fully expanded
                headerOffsetPx = newOffset.coerceIn(-(headerHeightPx - minCollapsedHeaderHeightPx), 0f)

                // Consume the scroll delta if the header is still collapsing or expanding
                return if (headerOffsetPx > -(headerHeightPx - minCollapsedHeaderHeightPx) && headerOffsetPx < 0f) {
                    Offset(0f, delta) // Consume the vertical scroll
                } else {
                    Offset.Zero // Don't consume if header is fully collapsed or expanded
                }
            }
        }
    }

    // Normalized scroll offset for alpha/other animations (0.0 fully expanded, 1.0 fully collapsed)
    val topBarAlpha = ((-headerOffsetPx) / (headerHeightPx - minCollapsedHeaderHeightPx)).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection) // Apply the nested scroll behavior to the Box
    ) {
        // The Parallax Image Header
        // It moves up with the scroll (its y offset changes)
        // and also has an internal parallax effect via its 'scrollOffsetPx' prop
        ParallaxImageHeader(
            imageRes = artPiece.imageRes,
            headerHeight = headerHeight,
            scrollOffsetPx = headerOffsetPx, // Pass the raw pixel offset for internal parallax calculation
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight) // Ensure this Box matches the ParallaxImageHeader's intended height
                .offset(y = (headerOffsetPx / LocalDensity.current.density).dp) // Move the header up as we scroll
        )

        // The scrollable content (details of the artwork)
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(top = headerHeight) // Start content below the initial header height
        ) {
            item {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface) // Important for content to not be transparent
                        .padding(all = 16.dp)
                ) {
                    // Artwork Title
                    Text(
                        text = artPiece.title,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Artist Name using DetailItem
                    DetailItem(
                        label = "Artist",
                        value = artPiece.artist,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Key Details (Year, Location, Material) using DetailItem
                    DetailItem(
                        label = when (artPiece.category) { // Dynamic label based on category
                            ArtCategory.PAINTING -> "Year"
                            ArtCategory.MURAL -> "Location & Year"
                            ArtCategory.SCULPTURE -> "Material & Period"
                        },
                        value = artPiece.details,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Section Title for Full Description
                    Text(
                        text = "About the Artwork",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold, // Or Bold
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )

                    // Full Description
                    Text(
                        text = artPiece.fullDescription,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp // Improves readability for longer text
                    )

                    // Add some space at the bottom to ensure all content can scroll above
                    // any bottom navigation bars or to give a bit of breathing room.
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        // The Collapsing TopAppBar - Sits on top and animates with the scroll
        TopAppBar(
            title = {
                Text(
                    artPiece.title,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = topBarAlpha), // Fade in title text
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        // Icon color changes for better visibility against the image vs. collapsed app bar
                        tint = Color.White.copy(alpha = 1f - topBarAlpha) // Fade to onSurface as bar appears
                            .compositeOver(MaterialTheme.colorScheme.onSurface.copy(alpha = topBarAlpha))
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = topBarAlpha), // Fade in background
                scrolledContainerColor = MaterialTheme.colorScheme.surface // Solid when fully scrolled/collapsed
            ),
            modifier = Modifier
                .fillMaxWidth()
            // The TopAppBar itself doesn't need an offset if the ParallaxImageHeader is offset correctly
            // and the LazyColumn starts below it. However, if you want the TopAppBar to "stick"
            // at a certain point during collapse, you might adjust its offset too.
            // For simplicity, here it just fades in/out.
            // If the ParallaxImageHeader is moving, this TopAppBar will appear to "stay" at the top
            // while its content (title, background) animates.
        )
    }
}

