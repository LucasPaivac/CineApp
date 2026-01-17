package com.example.cineapp.list.presentation.ui

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.cineapp.ApiMovieService
import com.example.cineapp.common.model.MovieDTO
import com.example.cineapp.common.model.MovieResponse
import com.example.cineapp.R
import com.example.cineapp.common.data.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MovieListScreen(navController: NavHostController) {

    var nowPlayingMovies by remember {
        mutableStateOf<List<MovieDTO>>(emptyList())
    }
    var popularMovies by remember {
        mutableStateOf<List<MovieDTO>>(emptyList())
    }
    var topRatedMovies by remember {
        mutableStateOf<List<MovieDTO>>(emptyList())
    }
    var upComingMovies by remember {
        mutableStateOf<List<MovieDTO>>(emptyList())
    }

    val apiService = RetrofitClient.retrofit.create(ApiMovieService::class.java)
    val callNowPlaying = apiService.getNowPlayingMovies()
    val callPopular = apiService.getPopularMovies()
    val callTopRated = apiService.getTopRatedMovies()
    val callUpComing = apiService.getUpComingMovies()

    callNowPlaying.enqueue(object : Callback<MovieResponse> {
        override fun onResponse(
            call: Call<MovieResponse?>,
            response: Response<MovieResponse?>
        ) {
            if (response.isSuccessful) {
                val responseNowMovies = response.body()?.results
                if (responseNowMovies != null) {
                    nowPlayingMovies = responseNowMovies
                }
            } else {
                Log.d("MainActivity", "Request error:${response.errorBody()}")
            }
        }

        override fun onFailure(
            call: Call<MovieResponse?>,
            t: Throwable
        ) {
            Log.d("MainActivity", "Network error:${t.message}")
        }

    })

    callPopular.enqueue(object : Callback<MovieResponse> {
        override fun onResponse(
            call: Call<MovieResponse?>,
            response: Response<MovieResponse?>
        ) {
            if (response.isSuccessful) {
                val responsePopularMovies = response.body()?.results
                if (responsePopularMovies != null) {
                    popularMovies = responsePopularMovies
                }
            } else {
                Log.d("MainActivity", "Request Error: ${response.errorBody()}")
            }
        }

        override fun onFailure(
            call: Call<MovieResponse?>,
            t: Throwable
        ) {
            Log.d("MainActivity", "Network Error: ${t.message}")
        }

    })

    callTopRated.enqueue(object : Callback<MovieResponse> {
        override fun onResponse(
            call: Call<MovieResponse?>,
            response: Response<MovieResponse?>
        ) {
            if (response.isSuccessful) {
                val responseTopRated = response.body()?.results
                if (responseTopRated != null) {
                    topRatedMovies = responseTopRated
                }
            } else {
                Log.d("MainActivity", "Request Error: ${response.errorBody()}")
            }
        }

        override fun onFailure(
            call: Call<MovieResponse?>,
            t: Throwable
        ) {
            Log.d("MainActivity", "NetworkError: ${t.message}")
        }

    })

    callUpComing.enqueue(object : Callback<MovieResponse> {
        override fun onResponse(
            call: Call<MovieResponse?>,
            response: Response<MovieResponse?>
        ) {
            if (response.isSuccessful) {
                val responseUpComing = response.body()?.results
                if (responseUpComing != null) {
                    upComingMovies = responseUpComing
                }
            } else {
                Log.d("MainActivity", "Request error: ${response.errorBody()}")
            }
        }

        override fun onFailure(
            call: Call<MovieResponse?>,
            t: Throwable
        ) {
            Log.d("MainActivity", "Network error: ${t.message}")
        }

    })

    MovieListContent(
        nowPlaying = nowPlayingMovies,
        popular = popularMovies,
        topRated = topRatedMovies,
        upcoming = upComingMovies,
    ) { itemClicked ->
        navController.navigate(route = "movieDetail/${itemClicked.id}")
    }

}

@Composable
private fun MovieListContent(
    nowPlaying: List<MovieDTO>,
    popular: List<MovieDTO>,
    topRated: List<MovieDTO>,
    upcoming: List<MovieDTO>,
    onClick: (MovieDTO) -> Unit
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
            movieList = nowPlaying,
            onClick = onClick
        )
        MovieSession(
            label = "Most Popular",
            movieList = popular,
            onClick = onClick
        )
        MovieSession(
            label = "Top Rated",
            movieList = topRated,
            onClick = onClick
        )
        MovieSession(
            label = "Upcoming",
            movieList = upcoming,
            onClick = onClick
        )

    }

}

@Composable
private fun MovieSession(
    label: String,
    movieList: List<MovieDTO>,
    onClick: (MovieDTO) -> Unit
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
        MovieList(movieList = movieList, onClick = onClick)

    }

}

@Composable
private fun MovieList(
    movieList: List<MovieDTO>,
    onClick: (MovieDTO) -> Unit
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
private fun MovieItem(movieDTO: MovieDTO, onClick: (MovieDTO) -> Unit) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .clickable {
                onClick.invoke(movieDTO)
            },
    ) {
        AsyncImage(
            modifier = Modifier
                .width(120.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = movieDTO.posterFullPath,
            contentDescription = "${movieDTO.title} Poster image"
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = movieDTO.title
        )
    }

}