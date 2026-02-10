package com.example.cineapp.screens.detail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.cineapp.screens.detail.MovieDetailViewModel
import com.example.cineapp.screens.detail.model.DetailUiState

@Composable
fun MovieDetailScreen(
    movieId: String,
    navHostController: NavHostController,
    detailViewModel: MovieDetailViewModel
) {

    val movieDetail by detailViewModel.uiMovieDetail.collectAsState()
    LaunchedEffect(movieId) {
        detailViewModel.fetchDetailMovie(movieId = movieId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                navHostController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back button"
                )
            }

            Text(
                modifier = Modifier
                    .padding(4.dp),
                text = movieDetail.movie.title
            )
        }

        MovieDetailContent(movieDetail)
    }
}

@Composable
private fun MovieDetailContent(
    detail: DetailUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = detail.movie.imagePoster,
            contentDescription = "${detail.movie.title} Poster image"
        )

        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = detail.movie.overview,
            fontSize = 16.sp
        )

    }
}