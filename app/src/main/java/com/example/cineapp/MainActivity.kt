package com.example.cineapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.cineapp.detail.presentation.MovieDetailViewModel
import com.example.cineapp.list.presentation.MovieListViewModel
import com.example.cineapp.ui.theme.CineAppTheme

class MainActivity : ComponentActivity() {

    private val listViewModel by viewModels<MovieListViewModel> { MovieListViewModel.Factory }
    private val detailViewModel by viewModels<MovieDetailViewModel>{ MovieDetailViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CineAppTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                    ) {
                        CineApp(
                            listViewModel = listViewModel,
                            detailViewModel = detailViewModel
                        )
                    }

                }
            }
        }
    }
}

