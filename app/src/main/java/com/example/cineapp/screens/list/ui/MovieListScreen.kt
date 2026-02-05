package com.example.cineapp.screens.list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.cineapp.R
import com.example.cineapp.common.model.MovieUiData
import com.example.cineapp.screens.list.model.MovieListUiEvent
import com.example.cineapp.screens.list.MovieListViewModel
import com.example.cineapp.screens.list.model.ListUiState

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CustomTopAppBar(
            logoImage = painterResource(R.drawable.logo),
            labelScreen = "Início",
            downloadIcon = painterResource(R.drawable.ic_download),
            searchIcon = painterResource(R.drawable.ic_search),
            onDownloadClick = { },
            onSearchClick = { }
        )

        Row() {
            OutlinedButton(onClick = {}) {
                Text("Séries")
            }

        }

        MovieSession(
            label = "Now Playing",
            listUiState = nowPlaying,
            onClick = onClick
        )
        MovieSession(
            label = "Most Popular",
            listUiState = popular,
            onClick = onClick
        )
        MovieSession(
            label = "Top Rated",
            listUiState = topRated,
            onClick = onClick
        )
        MovieSession(
            label = "Upcoming",
            listUiState = upcoming,
            onClick = onClick
        )

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
            .padding(8.dp)
    ) {
        Text(
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            text = label
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


    }

}

@Composable
private fun MovieList(
    movieList: List<MovieUiData>,
    onClick: (MovieUiData) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
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
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .clickable {
                onClick.invoke(movieUiData)
            },
    ) {
        AsyncImage(
            modifier = Modifier
                .width(120.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = movieUiData.image,
            contentDescription = "${movieUiData.title} Poster image"
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = movieUiData.title
        )
    }

}