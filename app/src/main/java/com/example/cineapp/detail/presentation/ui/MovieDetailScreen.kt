package com.example.cineapp.detail.presentation.ui

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.cineapp.ApiMovieService
import com.example.cineapp.common.model.MovieDTO
import com.example.cineapp.common.data.RetrofitClient
import com.example.cineapp.ui.theme.CineAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MovieDetailScreen(
    movieId: String,
    navHostController: NavHostController) {
    var movieDto by remember {
        mutableStateOf<MovieDTO?>(null)
    }

    val apiService = RetrofitClient.retrofit.create(ApiMovieService::class.java)

    apiService.getMovieDetail(movieId = movieId).enqueue(
        object : Callback<MovieDTO> {
            override fun onResponse(
                call: Call<MovieDTO?>,
                response: Response<MovieDTO?>
            ) {
                if (response.isSuccessful) {
                    movieDto = response.body()
                } else {
                    Log.d("MainActivity", "Request error:${response.errorBody()}")
                }
            }

            override fun onFailure(
                call: Call<MovieDTO?>,
                t: Throwable
            ) {
                Log.d("MainActivity", "Network error:${t.message}")
            }

        })

    movieDto?.let {

        Column(modifier = Modifier
            .fillMaxSize()) {

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
                    text = it.title
                )
            }

            MovieDetailContent(it)
        }
    }
}

@Composable
private fun MovieDetailContent(
    movieDTO: MovieDTO
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
            model = movieDTO.posterFullPath,
            contentDescription = "${movieDTO.title} Poster image"
        )

        Text(modifier = Modifier
            .padding(top = 8.dp),
            text = movieDTO.overview,
            fontSize = 16.sp
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailPreview() {

    CineAppTheme() {

    }

}