# Tarea3: Art Gallery Showcase

This application is a demonstration of an Art Gallery UI built with Kotlin and Jetpack Compose. It showcases different categories of art (Paintings, Murals, Sculptures), allows users to browse artworks within each category using a pager, and navigate to a detailed view for each piece. The detail screen features a parallax image header effect.

## 📑 Table of Contents

- [📝 Description](#-description)
- [✨ Features](#-features)
- [🛠️ Technologies Used](#️-technologies-used)
- [🚀 Installation](#-installation)
- [📱 Usage](#-usage)
- [📂 Project Structure](#-project-structure)
- [🖼️ Screenshots](#️-screenshots)
- [🤝 Contributing](#-contributing)
- [📄 License](#-license)

## 📝 Description

The application presents a main index screen allowing navigation to different art categories. Each category screen (Paintings, Murals, Sculptures) displays artworks in a horizontal pager. Users can select an artwork to view its details on a dedicated screen, which includes a dynamic parallax scrolling header and detailed information about the piece.

## ✨ Features

- **Art Categories**: Displays Paintings, Murals, and Sculptures.
- **Horizontal Pager**: Browse artworks within each category using a swipeable interface (`HorizontalPager`).
- **Detailed Artwork View**: Dedicated screen (`ArtDetailScreen`) for each artwork.
- **Parallax Image Header**: The `ArtDetailScreen` features a visually engaging parallax effect for the artwork image as the user scrolls.
- **Dynamic Content Display**: Information adapts based on the artwork type (e.g., year for paintings, location for murals).
- **Jetpack Compose UI**: Built entirely with modern declarative UI components.
    - `Scaffold`, `TopAppBar` for screen structure.
    - Custom components like `InfoCard`, `PagerIndicator`, `FullScreenImageWithGradient`, `ParallaxImageHeader`, `DetailItem`.
- **Compose Navigation**: Clear navigation flow between screens (`NavHost`, `NavController`).
- **Image Loading**: Efficient image loading using Coil.

## 🛠️ Technologies Used

- **Kotlin**: Main development language.
- **Jetpack Compose**: Modern declarative UI toolkit for Android.
- **Compose Navigation**: For navigating between composable screens.
- **Coil**: Image loading library for Kotlin.
- **Android Studio**: Integrated Development Environment.
- **Gradle**: Build automation tool.

## 🚀 Installation

1.  Clone the repository:
    ```bash
    git clone https://github.com/irmindev/MobileDevCourse.git 
    # Replace with your actual repository URL
    ```
2.  Open the `Tarea3` project in Android Studio.
3.  Ensure you have the correct Android SDK and tools installed.
4.  Sync the project with Gradle files.
5.  Run the app on an emulator or physical device.

## 📱 Usage

1.  Launch the application to see the main index screen.
2.  Select an art category (e.g., Paintings, Murals, Sculptures).
3.  On the category screen, swipe left or right to browse through the artworks.
4.  Tap the "VIEW DETAILS" button on an artwork's `InfoCard`.
5.  On the `ArtDetailScreen`, scroll up and down to observe the parallax effect on the image header and view the artwork's information.
6.  Use the back arrow in the `TopAppBar` to navigate to previous screens.

## 📂 Project Structure

```
Tarea3/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/tarea3/
│   │   │   │   ├── MainActivity.kt           # Main entry point
│   │   │   │   ├── ArtGalleryIndexScreen.kt  # Main screen linking to categories
│   │   │   │   ├── data/
│   │   │   │   │   ├── ArtData.kt            # Centralized artwork data source
│   │   │   │   │   ├── Models.kt             # Data classes (Painting, Mural, Sculpture)
│   │   │   │   │   └── ArtWork.kt            # (Potentially another model or sealed class for artwork)
│   │   │   │   ├── navigation/
│   │   │   │   │   ├── NavHost.kt            # Defines navigation graph
│   │   │   │   │   └── Routes.kt             # Sealed class for navigation routes
│   │   │   │   ├── ui/
│   │   │   │   │   ├── components/           # Reusable UI components
│   │   │   │   │   │   ├── DetailItem.kt
│   │   │   │   │   │   ├── FullScreenImageWithGradient.kt
│   │   │   │   │   │   ├── InfoCard.kt
│   │   │   │   │   │   ├── PagerIndicator.kt
│   │   │   │   │   │   └── ParallaxImageHeader.kt
│   │   │   │   │   ├── screens/              # Composable screens
│   │   │   │   │   │   ├── ArtDetailScreen.kt  # Screen for individual artwork details (with parallax)
│   │   │   │   │   │   ├── MuralsScreen.kt
│   │   │   │   │   │   ├── PaintingsScreen.kt
│   │   │   │   │   │   └── SculpturesScreen.kt
│   │   │   │   │   └── theme/                # UI Theme (Colors, Typography, Shapes)
│   │   │   ├── res/                          # Resources (drawables, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   ├── build.gradle.kts                      # Module-level build script
├── build.gradle.kts                          # Project-level build script
├── gradle/
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
└── README.md                                 # This file
```

## 🖼️ Screenshots
In the following section there's a snapshot of the project:

**Example:**
<!--
### Index Screen
![index_screen.png](assets/index_screen.png)

### Paintings Screen (Pager)
![paintings_screen.png](assets/paintings_screen.png)

### Art Detail Screen (Parallax)
![art_detail_screen.png](assets/art_detail_screen.png)
-->

## 🤝 Contributing

Contributions are welcome! Please open an issue or submit a pull request for suggestions, bug fixes, or improvements.

1.  Fork the Project.
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`).
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`).
4.  Push to the Branch (`git push origin feature/AmazingFeature`).
5.  Open a Pull Request.

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for details (you'll need to create this file if you want to specify a license).
