package com.example.cineapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.cineapp.screens.detail.MovieDetailViewModel
import com.example.cineapp.screens.list.MovieListViewModel
import com.example.cineapp.ui.theme.CineAppTheme


class MainActivity : ComponentActivity() {

    private val listViewModel by viewModels<MovieListViewModel> { MovieListViewModel.Factory }
    private val detailViewModel by viewModels<MovieDetailViewModel> { MovieDetailViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CineAppTheme {

                val snackBarHostState = remember { SnackbarHostState() }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(hostState = snackBarHostState)
                    }
                ) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .padding(top = innerPadding.calculateTopPadding())
                    ) {
                        CineApp(
                            snackbarHostState = snackBarHostState,
                            listViewModel = listViewModel,
                            detailViewModel = detailViewModel
                        )
                    }

                }
            }
        }
    }
}

