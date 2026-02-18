package com.example.cineapp

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cineapp.screens.detail.presentation.MovieDetailViewModel
import com.example.cineapp.screens.detail.presentation.ui.MovieDetailScreen
import com.example.cineapp.screens.list.presentation.MovieListViewModel
import com.example.cineapp.screens.list.presentation.ui.MovieListScreen

@Composable
fun CineApp(
    snackbarHostState: SnackbarHostState,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "movieList") {
        composable(route = "movieList") {
            MovieListScreen(
                navController = navController,
                snackbarHostState = snackbarHostState,
            )
        }

        composable(
            route = "movieDetail" + "/{itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val movieId = requireNotNull(backStackEntry.arguments?.getString("itemId"))
            MovieDetailScreen(
                movieId = movieId,
            )
        }
    }
}