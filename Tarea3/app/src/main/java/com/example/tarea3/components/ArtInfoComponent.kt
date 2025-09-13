package com.example.tarea3.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.tarea3.model.ArtWork

@Composable
fun ArtInfoComponent(artWork: ArtWork) {
    Column(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = rememberAsyncImagePainter(artWork.imageUrl),
            contentDescription = artWork.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = artWork.title)
        Text(text = "Autor: ${artWork.author}")
        Text(text = "Año: ${artWork.year}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = artWork.description)
    }
}
