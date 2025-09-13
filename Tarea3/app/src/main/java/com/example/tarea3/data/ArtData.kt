package com.example.tarea3.data

import com.example.tarea3.R

object ArtData {
    val paintings = listOf(
        Painting("The Starry Night", "Vincent van Gogh", "1889", R.drawable.painting_starry_night),
        Painting("Mona Lisa", "Leonardo da Vinci", "c. 1503–1506", R.drawable.painting_mona_lisa),
        Painting("Girl with a Pearl Earring", "Johannes Vermeer", "c. 1665", R.drawable.painting_pearl_earring),
        Painting("The Persistence of Memory", "Salvador Dalí", "1931", R.drawable.painting_persistence_memory),
        Painting("Water Lilies", "Claude Monet", "c. 1915", R.drawable.painting_water_lilies)
    )

    val murals = listOf(
        Mural("Ethnicities (Etnias)", "Eduardo Kobra", "Rio de Janeiro, Brazil", R.drawable.mural_ethnicities),
        Mural("The Detroit Industry Murals", "Diego Rivera", "Detroit, USA", R.drawable.mural_detroit_industry),
        Mural("Tuttomondo", "Keith Haring", "Pisa, Italy", R.drawable.mural_tuttomondo),
        Mural("Rage, the Flower Thrower", "Banksy", "Jerusalem", R.drawable.mural_flower_thrower),
        Mural("Epic of American Civilization", "José Clemente Orozco", "New Hampshire, USA", R.drawable.mural_epic_civilization)
    )

    val sculptures = listOf(
        Sculpture("David", "Michelangelo", "Marble", R.drawable.sculpture_david),
        Sculpture("The Thinker", "Auguste Rodin", "Bronze", R.drawable.sculpture_the_thinker),
        Sculpture("Venus de Milo", "Alexandros of Antioch", "Marble", R.drawable.sculpture_venus_de_milo),
        Sculpture("Christ the redeemer", "Paul Landowski", "Marble", R.drawable.sculpture_christ_the_redeemer),
        Sculpture("Cloud Gate", "Anish Kapoor", "Stainless Steel", R.drawable.sculpture_cloud_gate)
    )

    fun findArtwork(title: String): Any? {
        return paintings.find { it.title == title }
            ?: murals.find { it.title == title }
            ?: sculptures.find { it.title == title }
    }
}
