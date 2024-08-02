
# MovieApp

MovieApp is an Android application that showcases a list of movies in different categories: Popular, Top Rated, Upcoming, and Now Playing. The application follows the MVVM architecture and Clean Architecture principles to ensure a modular, maintainable, and testable codebase. The app uses modern Android development tools and libraries, including NavController for navigation, Koin for dependency injection, Coil for image caching, and Retrofit for networking.

## Table of Contents

- [Screenshots](#screenshots)
- [Features](#features)
- [Architecture](#architecture)
  - [Data Layer](#data-layer)
  - [Domain Layer](#domain-layer)
  - [Presentation Layer](#presentation-layer)
- [Libraries](#libraries)
- [Setup](#setup)
- [Usage](#usage)

## Screenshots

### Home Screen
![home_screen](https://github.com/user-attachments/assets/85c80c15-30d0-4e41-b32a-e6d4fa2c911a)

### Movie Details Screen
![detail_screen](https://github.com/user-attachments/assets/c621aee5-c1f0-47b4-be65-c868e070a5ab)

## Features

- Display a list of movies in four categories: Popular, Top Rated, Upcoming, and Now Playing.
- Navigate between screens using NavController.
- Efficient image loading and caching with Coil.
- Dependency injection with Koin.
- Networking with Retrofit.

## Architecture

The application follows the MVVM (Model-View-ViewModel) architecture pattern and Clean Architecture principles. The project is divided into three main layers: Data, Domain, and Presentation.

### Data Layer

The Data layer is responsible for fetching data from various sources (network, cache, etc.) and providing it to the Domain layer. It includes:

- **API**: Defines the endpoints for the movie API.
- **Mappers**: Maps data from network models to domain models.
- **Models**: Contains the data models used in the Data layer.
- **Repositories**: Provides data to the Domain layer.

#### Example

```kotlin
// MoviesService.kt
interface MoviesService {  
  @GET("movie/popular")
  suspend fun getPopularMovies(
    @HeaderMap headers: Map<String, String>,  
    @Query("page") page: Int,
  ): MovieListModel
  // other endpoints...
}

// MovieRepositoryImpl.kt
class MoviesRepositoryImpl(  
  private val moviesService: MoviesService,  
) : MoviesRepository {

  override suspend fun getPopularMovies(page: Int): MovieList? {  
    return try {  
      val popularMovies = moviesService  
        .getPopularMovies(headers, page)  
      val toMovieList = popularMovies  
        .toMovieList()  
      toMovieList  
    } catch (e: Exception) {  
      Log.e("MoviesRepositoryImpl", "getPopularMovies: ${e.localizedMessage}")  
      null  
    }  
  }
  // other methods...
}
```

### Domain Layer

The Domain layer contains the business logic of the application. It includes:

- **Models**: The core data models used throughout the application.
- **Repositories**: Interfaces that define the methods for data operations.

#### Example

```kotlin
// MovieList.kt
data class MovieList(  
  val dates: Dates?,  
  val page: Int,  
  val results: List<Movie>,  
  val totalPages: Int,  
  val totalResults: Int,  
)

// MoviesRepository.kt
interface MoviesRepository {  
  suspend fun getPopularMovies(page: Int): MovieList?
  // other methods...
}
```

### Presentation Layer

The Presentation layer is responsible for displaying data to the user and handling user interactions. It includes:

- **Theme**: Defines the UI theme for the application.
- **UI**: Contains the composables for the UI.
- **Utils**: Utility classes and extensions for the UI layer.

#### Example

```kotlin
// MovieViewModel.kt
class MoviesViewModel(  
  private val moviesRepository: MoviesRepository,  
) : ViewModel() {
  var popularMovies by mutableStateOf(listOf<Movie>())

  init {  
    getPopularMovies()  
  }

  private fun getNowPlayingMovies(page: Int = 1) = viewModelScope.launch {  
    val movies = withContext(Dispatchers.IO) {  
      moviesRepository  
        .getNowPlayingMovies(page)  
        ?.results  
        ?: emptyList()  
    }  
    nowPlayingMovies = movies  
  }
  // other ViewModel logic...
}

// MovieListScreen.kt
@Composable  
fun MoviesScreen(  
  viewModel: MoviesViewModel = getViewModel<MoviesViewModel>(),  
  navController: NavController  
) {
  Column(  
    modifier = Modifier  
      .imePadding()  
      .background(Color.Black)  
      .fillMaxSize()  
      .verticalScroll(scrollState)  
  ) {
    Spacer(  
      Modifier.windowInsetsTopHeight(  
        WindowInsets.statusBars  
      )  
    )
    MovieCategory(  
      title = "Popular",  
      movies = viewModel.popularMovies,  
      navController = navController  
    )  
    Spacer(  
      Modifier.windowInsetsBottomHeight(  
        WindowInsets.systemBars  
      )  
    )  
  }  
}
```

## Libraries

- **[NavController](https://developer.android.com/jetpack/compose/navigation)**: For navigation between composables.
- **[Koin](https://insert-koin.io/)**: For dependency injection.
- **[Coil](https://coil-kt.github.io/coil/)**: For image loading and caching.
- **[Retrofit](https://square.github.io/retrofit/)**: For networking.

## Setup

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/MovieApp.git
   cd MovieApp
   ```

2. Open the project in Android Studio.

3. Build the project to install the necessary dependencies.

4. Obtain an API key from [The Movie Database (TMDb)](https://www.themoviedb.org/documentation/api) and add it to your `local.properties` file:
   ```properties
   TMDB_API_KEY=your_api_key
   ```

5. Run the application on an emulator or physical device.

## Usage

- Launch the app to see the list of movies in different categories.
- Click on a movie to see its details.
- Navigate back and forth between the movie list and movie details using the back button.
