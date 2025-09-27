package com.example.tarea3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
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
import androidx.navigation.NavController
import com.example.tarea3.data.ArtCategory
import com.example.tarea3.data.ArtPiece
import com.example.tarea3.ui.components.DetailItem
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtDetailScreen(
    navController: NavController,
    artPiece: ArtPiece,
    isDarkMode: Boolean = false,
    onToggleTheme: () -> Unit = {}
) {
    val lazyListState = rememberLazyListState()
    val headerHeight = 320.dp
    val headerHeightPx = with(LocalDensity.current) { headerHeight.toPx() }
    val minCollapsedHeaderHeightPx = with(LocalDensity.current) { TopAppBarDefaults.TopAppBarExpandedHeight.toPx() }

    var headerOffsetPx by remember { mutableFloatStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = headerOffsetPx + delta
                headerOffsetPx = newOffset.coerceIn(-(headerHeightPx - minCollapsedHeaderHeightPx), 0f)
                return if (headerOffsetPx > -(headerHeightPx - minCollapsedHeaderHeightPx) && headerOffsetPx < 0f) {
                    Offset(0f, delta)
                } else {
                    Offset.Zero
                }
            }
        }
    }

    val topBarAlpha = ((-headerOffsetPx) / (headerHeightPx - minCollapsedHeaderHeightPx)).coerceIn(0f, 1f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .nestedScroll(nestedScrollConnection)
    ) {
        Box(modifier = Modifier.height(headerHeight)) {
            AsyncImage(
                model = artPiece.imageRes,
                contentDescription = artPiece.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
                    .offset(y = (headerOffsetPx / LocalDensity.current.density).dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(headerHeight)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                            ),
                            startY = 0f,
                            endY = 800f
                        )
                    )
            )
        }
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(top = headerHeight)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .shadow(8.dp, shape = MaterialTheme.shapes.medium),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(20.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = artPiece.title,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { /* TODO: Acción de favorito */ }) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Favorito",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row {
                            AssistChip(
                                onClick = {},
                                label = { Text(text = artPiece.category.name) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                ),
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            AssistChip(
                                onClick = {},
                                label = { Text(text = artPiece.artist) },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
                        Spacer(modifier = Modifier.height(12.dp))
                        DetailItem(
                            label = when (artPiece.category) {
                                ArtCategory.PAINTING -> "Año"
                                ArtCategory.MURAL -> "Ubicación & Año"
                                ArtCategory.SCULPTURE -> "Material & Periodo"
                            },
                            value = artPiece.details,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = "Sobre la obra",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                        )
                        Text(
                            text = artPiece.fullDescription,
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = 24.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(onClick = { /* TODO: Acción de compartir */ }) {
                                Icon(
                                    imageVector = Icons.Filled.Share,
                                    contentDescription = "Compartir",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        TopAppBar(
            title = {
                Text(
                    artPiece.title,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = topBarAlpha),
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Atrás",
                        tint = Color.White.copy(alpha = 1f - topBarAlpha)
                            .compositeOver(MaterialTheme.colorScheme.onSurface.copy(alpha = topBarAlpha))
                    )
                }
            },
            actions = {
                IconButton(onClick = onToggleTheme) {
                    Icon(
                        imageVector = if (isDarkMode) Icons.Filled.Star else Icons.Filled.FavoriteBorder,
                        contentDescription = if (isDarkMode) "Cambiar a modo claro" else "Cambiar a modo oscuro",
                        tint = Color.White.copy(alpha = 1f - topBarAlpha)
                            .compositeOver(MaterialTheme.colorScheme.primary.copy(alpha = topBarAlpha))
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = topBarAlpha),
                scrolledContainerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
