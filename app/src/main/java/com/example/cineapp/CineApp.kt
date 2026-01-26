package com.example.cineapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cineapp.detail.presentation.MovieDetailViewModel
import com.example.cineapp.detail.presentation.ui.MovieDetailScreen
import com.example.cineapp.list.presentation.MovieListViewModel
import com.example.cineapp.list.presentation.ui.MovieListScreen

@Composable
fun CineApp(
    listViewModel: MovieListViewModel,
    detailViewModel: MovieDetailViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "movieList") {
        composable(route = "movieList") {
            MovieListScreen(navController = navController, viewModel = listViewModel)
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
                navHostController = navController,
                detailViewModel = detailViewModel
            )
        }
    }
}