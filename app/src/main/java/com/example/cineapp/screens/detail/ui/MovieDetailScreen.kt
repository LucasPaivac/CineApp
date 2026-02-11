package com.example.cineapp.screens.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.cineapp.screens.detail.MovieDetailViewModel
import com.example.cineapp.screens.detail.model.CastUiData
import com.example.cineapp.screens.detail.model.DetailUiState
import com.example.cineapp.ui.theme.backgroundDark
import com.example.cineapp.ui.theme.surfaceDark

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


    MovieDetailContent(movieDetail)

}


@Composable
private fun MovieDetailContent(
    detail: DetailUiState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        //FeaturedImage and Title
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillParentMaxHeight(0.5f),
                shape = RoundedCornerShape(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize(),
                        model = detail.movie.imageBanner,
                        contentDescription = "${detail.movie.title} image banner",
                        contentScale = ContentScale.FillHeight,
                        alignment = Alignment.TopCenter
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    0.4f to Color.Transparent,
                                    0.7f to surfaceDark.copy(alpha = 0.5f),
                                    0.9f to backgroundDark,
                                    startY = 20f
                                )
                            )
                    )

                    Text(
                        modifier = Modifier
                            .padding(18.dp)
                            .align(Alignment.BottomCenter),
                        text = detail.movie.title,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displayMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        //Chips with movie information
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoChip(detail.movie.runtime)
                InfoChip(detail.movie.releaseYear)
                InfoChip(detail.movie.voteAverage)
                InfoChip(detail.movie.genre)
            }
        }

        //Title Description
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        //Description
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                text = detail.movie.overview,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        //Title Main Cast List
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                text = "Main cast",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        //Main Cast List
        items(detail.movie.cast){
            CastItem(
                it
            )
            Spacer(modifier = Modifier.size(8.dp))
        }

        item { Spacer(modifier = Modifier.size(64.dp)) }
    }
}

@Composable
fun InfoChip(info: String) {
    SuggestionChip(
        onClick = {},
        label = {
            Text(
                text = info,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White
            )
        },
        border = SuggestionChipDefaults.suggestionChipBorder(
            enabled = true,
            borderColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp),
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = SuggestionChipDefaults.suggestionChipElevation(16.dp)
    )
}

@Composable
fun CastItem(cast: CastUiData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .width(64.dp)
                .height(64.dp)
                .clip(CircleShape),
            model = cast.profileImage,
            contentScale = ContentScale.Crop,
            contentDescription = "${cast.name} image"
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            text = "${cast.name} - '${cast.character}'",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

