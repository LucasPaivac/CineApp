package com.example.cineapp.screens.list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.cineapp.R
import com.example.cineapp.common.model.MovieUiData
import com.example.cineapp.screens.list.MovieListViewModel
import com.example.cineapp.screens.list.model.ListUiState
import com.example.cineapp.screens.list.model.MovieListUiEvent
import com.example.cineapp.ui.theme.backgroundDark

@Composable
fun MovieListScreen(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    viewModel: MovieListViewModel
) {

    val nowPlayingMovies by viewModel.uiNowPlaying.collectAsState()
    val popularMovies by viewModel.uiMostPopular.collectAsState()
    val topRatedMovies by viewModel.uiTopRated.collectAsState()
    val upComingMovies by viewModel.uiUpComing.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is MovieListUiEvent.NavigateToDetails -> {
                    navController.navigate(route = "movieDetail/${event.movieId}")
                }

                is MovieListUiEvent.ShowMessage -> {
                    snackbarHostState.showSnackbar(event.message)
                    viewModel.onSnackbarDismissed()
                }
            }
        }
    }

    MovieListContent(
        nowPlaying = nowPlayingMovies,
        popular = popularMovies,
        topRated = topRatedMovies,
        upcoming = upComingMovies,
    ) { itemClicked ->
        viewModel.onMovieClicked(itemClicked.id)
    }

}

@Composable
private fun MovieListContent(
    nowPlaying: ListUiState,
    popular: ListUiState,
    topRated: ListUiState,
    upcoming: ListUiState,
    onClick: (MovieUiData) -> Unit
) {
    LazyColumn(
        modifier =
            Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            CustomTopAppBar(
                labelScreen = stringResource(R.string.app_name),
                downloadIcon = painterResource(R.drawable.ic_download),
                searchIcon = painterResource(R.drawable.ic_search),
                menuIcon = painterResource(R.drawable.ic_outline_menu),
                onDownloadClick = { },
                onSearchClick = { }
            )
        }

        item {
            popular.list.firstOrNull()?.let {
                MovieFeatured(it, onClick)
            }
        }

        item {
            Spacer(modifier = Modifier.size(16.dp))
        }

        item {
            MovieSession(
                label = "Now Playing",
                listUiState = nowPlaying,
                onClick = onClick
            )
        }

        item { MovieSession(label = "Most Popular", listUiState = popular, onClick = onClick) }

        item { MovieSession(label = "Top Rated", listUiState = topRated, onClick = onClick) }

        item { MovieSession(label = "Upcoming", listUiState = upcoming, onClick = onClick) }

    }

}

@Composable
private fun MovieSession(
    label: String,
    listUiState: ListUiState,
    onClick: (MovieUiData) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            fontWeight = FontWeight.SemiBold,
            text = label,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(8.dp))

        if (listUiState.isLoading) {

        } else if (listUiState.isError) {
            Text(
                text = listUiState.errorMessage ?: "",
                color = Color.Red
            )
        } else {
            MovieList(movieList = listUiState.list, onClick = onClick)
        }

        Spacer(modifier = Modifier.size(24.dp))
    }

}

@Composable
private fun MovieList(
    movieList: List<MovieUiData>,
    onClick: (MovieUiData) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
        items(movieList) {
            MovieItem(
                it,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun MovieItem(movieUiData: MovieUiData, onClick: (MovieUiData) -> Unit) {

    ElevatedCard(
        modifier = Modifier
            .width(105.dp)
            .height(150.dp)
            .clickable {
                onClick.invoke(movieUiData)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = movieUiData.imagePoster,
            contentDescription = "${movieUiData.title} Poster image"
        )
    }

}


@Composable
private fun MovieFeatured(movieUiData: MovieUiData, onClick: (MovieUiData) -> Unit) {

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .shadow(
                elevation = 20.dp,
                spotColor = Color.Black,
            )
            .clickable {
                onClick.invoke(movieUiData)
            },
        shape = RoundedCornerShape(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart,

            ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                model = movieUiData.imageBanner,
                contentDescription = "${movieUiData.title} Banner image"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                            startY = 50f
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.Bottom
            ) {

                ElevatedCard(
                    modifier = Modifier
                        .width(120.dp)
                        .height(150.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 16.dp
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        model = movieUiData.imagePoster,
                        contentDescription = "${movieUiData.title} Poster image",
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))

                Text(
                    text = movieUiData.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = Color.White,
                    maxLines = 2
                )
            }

        }
    }


}
