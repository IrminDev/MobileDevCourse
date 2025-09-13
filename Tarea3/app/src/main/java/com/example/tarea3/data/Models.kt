
package com.example.tarea3.data

import androidx.annotation.DrawableRes

data class Painting(
    val title: String,
    val artist: String,
    val year: String,
    @DrawableRes
    val imageRes: Int
)

data class Mural(
    val title: String,
    val artist: String,
    val location: String,
    @DrawableRes
    val imageRes: Int
)

data class Sculpture(
    val title: String,
    val artist: String,
    val material: String,
    @DrawableRes
    val imageRes: Int
)
