package com.example.tarea3.data

import androidx.annotation.DrawableRes
import com.example.tarea3.R

// A unified data class for any piece of art
data class ArtPiece(
    val id: String, // Unique identifier (e.g., "starry_night", "ethnicities")
    val title: String,
    val artist: String,
    val shortDescription: String, // A brief summary for previews
    val details: String, // Key info like year for paintings, location for murals, material for sculptures
    val fullDescription: String, // A more detailed paragraph for the detail screen
    @DrawableRes val imageRes: Int,
    val category: ArtCategory
)

enum class ArtCategory {
    PAINTING, MURAL, SCULPTURE
}

// A central object to hold and provide all art data
object ArtRepository {
    private val allArtPieces = listOf(

        // Paintings
        ArtPiece(
            id = "starry_night",
            title = "The Starry Night",
            artist = "Vincent van Gogh",
            shortDescription = "Iconic Post-Impressionist painting known for its swirling sky.",
            details = "1889",
            fullDescription = """
                Painted in June 1889, "The Starry Night" depicts the view from the east-facing window of Van Gogh's 
                asylum room at Saint-Rémy-de-Provence, just before sunrise, with the addition of an imaginary village. 
                It has been in the permanent collection of the Museum of Modern Art in New York City since 1941. 
                The painting is among Van Gogh's most well-known works and marks a decisive turn towards greater 
                imaginative freedom in his art. The vibrant colors and dynamic brushstrokes create a sense of movement 
                and emotional intensity.
            """.trimIndent(),
            imageRes = R.drawable.painting_starry_night, // Ensure this drawable exists
            category = ArtCategory.PAINTING
        ),
        ArtPiece(
            id = "mona_lisa",
            title = "Mona Lisa",
            artist = "Leonardo da Vinci",
            shortDescription = "The world's most famous portrait, celebrated for its enigmatic smile.",
            details = "c. 1503–1506",
            fullDescription = """
                The Mona Lisa is a half-length portrait painting by Italian artist Leonardo da Vinci. 
                Considered an archetypal masterpiece of the Italian Renaissance, it has been described as "the best known, 
                the most visited, the most written about, the most sung about, the most parodied work of art in the world".
                Its fame rests, in particular, on the elusive quality of the sitter's expression, which is frequently 
                described as enigmatic. Leonardo's use of sfumato, a subtle blurring of outlines, contributes to this effect.
            """.trimIndent(),
            imageRes = R.drawable.painting_mona_lisa, // Ensure this drawable exists
            category = ArtCategory.PAINTING
        ),
        ArtPiece(
            id = "pearl_earring",
            title = "Girl with a Pearl Earring",
            artist = "Johannes Vermeer",
            shortDescription = "Captivating Dutch Golden Age painting, famous for its use of light.",
            details = "c. 1665",
            fullDescription = """
                "Girl with a Pearl Earring" is an oil painting by Dutch Golden Age painter Johannes Vermeer. 
                Dated c. 1665, the painting has been in the collection of the Mauritshuis in The Hague since 1902. 
                It is a tronie of a young woman with a headscarf and a pearl earring. The painting is renowned for its 
                delicate handling of light, the mysterious gaze of the subject, and the luminous pearl.
            """.trimIndent(),
            imageRes = R.drawable.painting_pearl_earring, // Ensure this drawable exists
            category = ArtCategory.PAINTING
        ),
        ArtPiece(
            id = "persistence_memory",
            title = "The Persistence of Memory",
            artist = "Salvador Dalí",
            shortDescription = "The quintessential surrealist painting with its melting clocks.",
            details = "1931",
            fullDescription = """
                "The Persistence of Memory" is a 1931 painting by artist Salvador Dalí, and one of the most recognizable 
                works of Surrealism. First shown at the Julien Levy Gallery in 1932, since 1934 the painting has been in 
                the collection of the Museum of Modern Art (MoMA) in New York City. The iconic image of melting pocket 
                watches symbolizes the relativity of space and time, a concept Dalí explored throughout his career.
            """.trimIndent(),
            imageRes = R.drawable.painting_persistence_memory, // Ensure this drawable exists
            category = ArtCategory.PAINTING
        ),
        ArtPiece(
            id = "water_lilies",
            title = "Water Lilies",
            artist = "Claude Monet",
            shortDescription = "A serene and iconic series of Impressionist paintings.",
            details = "c. 1915", // Monet painted many Water Lilies series over decades
            fullDescription = """
                "Water Lilies" (or Nymphéas) is a series of approximately 250 oil paintings by French Impressionist 
                Claude Monet. The paintings depict his flower garden at his home in Giverny, and were the main focus 
                of his artistic production during the last thirty years of his life. Many of the works were painted 
                while Monet suffered from cataracts. The series is celebrated for its immersive quality and Monet's 
                masterful capture of light and atmosphere.
            """.trimIndent(),
            imageRes = R.drawable.painting_water_lilies, // Ensure this drawable exists
            category = ArtCategory.PAINTING
        ),

        // Murals
        ArtPiece(
            id = "ethnicities",
            title = "Ethnicities (Etnias)",
            artist = "Eduardo Kobra",
            shortDescription = "A vibrant mural representing global unity for the Rio Olympics.",
            details = "Rio de Janeiro, Brazil, 2016",
            fullDescription = """
                Created for the 2016 Rio Olympics, this colossal mural by Brazilian artist Eduardo Kobra 
                spans over 30,000 square feet. It features five portraits representing indigenous peoples 
                from five continents: the Huli from Papua New Guinea (Oceania), the Mursi from Ethiopia (Africa), 
                the Kayin from Thailand (Asia), the Supi from Europe, and the Tapajós from the Americas.
                It held the Guinness World Record for the largest spray paint mural by a team, symbolizing global unity.
            """.trimIndent(),
            imageRes = R.drawable.mural_ethnicities, // Ensure this drawable exists
            category = ArtCategory.MURAL
        ),
        ArtPiece(
            id = "detroit_industry",
            title = "Detroit Industry Murals",
            artist = "Diego Rivera",
            shortDescription = "Monumental work depicting the industrial culture of Detroit.",
            details = "Detroit, USA, 1933",
            fullDescription = """
                The Detroit Industry Murals are a series of frescoes by the Mexican artist Diego Rivera, 
                depicting industry at the Ford Motor Company and in Detroit. Painted between 1932 and 1933, 
                they are considered a masterpiece of the Mexican Muralism movement. The murals cover all four walls 
                of the Garden Court at the Detroit Institute of Arts, portraying the interconnectedness of man and machine.
            """.trimIndent(),
            imageRes = R.drawable.mural_detroit_industry, // Ensure this drawable exists
            category = ArtCategory.MURAL
        ),
        ArtPiece(
            id = "tuttomondo",
            title = "Tuttomondo",
            artist = "Keith Haring",
            shortDescription = "A colorful celebration of peace and harmony in Pisa.",
            details = "Pisa, Italy, 1989",
            fullDescription = """
                "Tuttomondo" (meaning "All World") is Keith Haring's last public mural, created in 1989 on the 
                exterior wall of the Sant'Antonio Abate church in Pisa, Italy. The vibrant artwork features 30 figures, 
                each representing a different aspect of life and peace. It's a powerful message of global harmony 
                and one of Haring's most significant and beloved works.
            """.trimIndent(),
            imageRes = R.drawable.mural_tuttomondo, // Ensure this drawable exists
            category = ArtCategory.MURAL
        ),
        ArtPiece(
            id = "flower_thrower",
            title = "Rage, the Flower Thrower",
            artist = "Banksy",
            shortDescription = "Iconic stencil graffiti symbolizing peace and protest.",
            details = "Jerusalem, 2005",
            fullDescription = """
                "Rage, the Flower Thrower" or "Love is in the Air" is one of Banksy's most famous stencil graffiti works. 
                It depicts a masked Palestinian throwing a bouquet of flowers, instead of a Molotov cocktail. 
                Created in Jerusalem in 2005 on the West Bank wall, it's a powerful statement about peace and protest 
                in a conflict zone, characteristic of Banksy's politically charged street art.
            """.trimIndent(),
            imageRes = R.drawable.mural_flower_thrower, // Ensure this drawable exists
            category = ArtCategory.MURAL
        ),
        ArtPiece(
            id = "epic_civilization",
            title = "The Epic of American Civilization",
            artist = "José Clemente Orozco",
            shortDescription = "Powerful and complex mural series at Dartmouth College.",
            details = "New Hampshire, USA, 1932-1934",
            fullDescription = """
                "The Epic of American Civilization" is a large mural cycle by Mexican artist José Clemente Orozco, 
                located in the Baker Memorial Library at Dartmouth College in Hanover, New Hampshire. 
                Painted between 1932 and 1934, the series comprises 24 panels depicting the history of the Americas, 
                from ancient indigenous civilizations to the impact of industrialization. It is a complex and often 
                critical portrayal of historical events.
            """.trimIndent(),
            imageRes = R.drawable.mural_epic_civilization, // Ensure this drawable exists
            category = ArtCategory.MURAL
        ),

        // Sculptures
        ArtPiece(
            id = "david",
            title = "David",
            artist = "Michelangelo",
            shortDescription = "Renaissance masterpiece representing the biblical hero David.",
            details = "Marble, 1501-1504",
            fullDescription = """
                Michelangelo's "David" is a masterpiece of Renaissance sculpture, created between 1501 and 1504. 
                The 5.17-metre (17.0 ft) marble statue represents the Biblical hero David, just before his battle with Goliath. 
                It is renowned for its depiction of idealized human form, anatomical precision, and intense expression. 
                Originally commissioned for Florence Cathedral, it became a symbol of the Florentine Republic's strength and freedom.
            """.trimIndent(),
            imageRes = R.drawable.sculpture_david, // Ensure this drawable exists
            category = ArtCategory.SCULPTURE
        ),
        ArtPiece(
            id = "the_thinker",
            title = "The Thinker",
            artist = "Auguste Rodin",
            shortDescription = "Famous bronze sculpture symbolizing philosophy and deep thought.",
            details = "Bronze, conceived 1880, cast 1904",
            fullDescription = """
                "The Thinker" (Le Penseur) is a bronze sculpture by Auguste Rodin, usually placed on a stone pedestal. 
                The work shows a nude male figure of heroic size sitting on a rock with his chin resting on one hand as 
                though deep in thought, and is often used as an image to represent philosophy. Rodin conceived the figure 
                as part of his monumental work "The Gates of Hell" but it became famous as an independent piece.
            """.trimIndent(),
            imageRes = R.drawable.sculpture_the_thinker, // Ensure this drawable exists
            category = ArtCategory.SCULPTURE
        ),
        ArtPiece(
            id = "venus_de_milo",
            title = "Venus de Milo",
            artist = "Alexandros of Antioch",
            shortDescription = "Celebrated ancient Greek sculpture known for its mysterious beauty.",
            details = "Marble, c. 130–100 BC",
            fullDescription = """
                The "Venus de Milo" is an ancient Greek statue and one of the most famous works of ancient Greek sculpture. 
                Created sometime between 130 and 100 BC, it is believed to depict Aphrodite, the Greek goddess of love 
                and beauty (Venus to the Romans). The marble statue is greater than life size at 203 cm (6 ft 8 in) high. 
                Its arms are missing, and their original pose is unknown, adding to its mystique. It is currently on 
                permanent display at the Louvre Museum in Paris.
            """.trimIndent(),
            imageRes = R.drawable.sculpture_venus_de_milo, // Ensure this drawable exists
            category = ArtCategory.SCULPTURE
        ),
        ArtPiece(
            id = "christ_the_redeemer",
            title = "Christ the Redeemer",
            artist = "Paul Landowski (sculptor), Heitor da Silva Costa (engineer)",
            shortDescription = "Colossal Art Deco statue overlooking Rio de Janeiro.",
            details = "Soapstone, Rio de Janeiro, Brazil, 1931",
            fullDescription = """
                "Christ the Redeemer" is an Art Deco statue of Jesus Christ in Rio de Janeiro, Brazil, created by 
                French sculptor Paul Landowski and built by Brazilian engineer Heitor da Silva Costa, in collaboration 
                with French engineer Albert Caquot. Romanian sculptor Gheorghe Leonida fashioned the face. 
                Constructed between 1922 and 1931, the statue is 30 metres (98 ft) high, excluding its 8-metre (26 ft) pedestal. 
                The arms stretch 28 metres (92 ft) wide. It is a symbol of Christianity worldwide and a cultural icon of both Rio de Janeiro and Brazil.
            """.trimIndent(),
            imageRes = R.drawable.sculpture_christ_the_redeemer, // Ensure this drawable exists
            category = ArtCategory.SCULPTURE
        ),
        ArtPiece(
            id = "cloud_gate",
            title = "Cloud Gate",
            artist = "Anish Kapoor",
            shortDescription = "Contemporary public sculpture in Chicago, known as 'The Bean'.",
            details = "Stainless Steel, Chicago, USA, 2006",
            fullDescription = """
                "Cloud Gate", affectionately known as "The Bean," is a public sculpture by Indian-born British artist 
                Sir Anish Kapoor, located in Millennium Park in Chicago, Illinois. Constructed between 2004 and 2006, 
                the sculpture is made up of 168 stainless steel plates welded together, and its highly polished exterior 
                has no visible seams. It measures 33 by 66 by 42 feet (10 by 20 by 13 m), and weighs 110 short tons. 
                Its reflective surface offers fascinating distortions of the city skyline and visitors.
            """.trimIndent(),
            imageRes = R.drawable.sculpture_cloud_gate, // Ensure this drawable exists
            category = ArtCategory.SCULPTURE
        )
    )

    fun getArtPieces(category: ArtCategory): List<ArtPiece> {
        return allArtPieces.filter { it.category == category }
    }

    fun findArtPieceById(id: String): ArtPiece? {
        return allArtPieces.find { it.id == id }
    }
}
