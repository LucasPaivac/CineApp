package com.example.cineapp.detail

import android.accounts.NetworkErrorException
import app.cash.turbine.test
import com.example.cineapp.common.model.MovieDetail
import com.example.cineapp.common.repository.MovieRepository
import com.example.cineapp.common.utils.toMovieUiData
import com.example.cineapp.screens.detail.MovieDetailViewModel
import com.example.cineapp.screens.detail.model.DetailUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.net.UnknownHostException


class MovieDetailViewModelTest {

    private val repository: MovieRepository = mock()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    private val underTest by lazy {
        MovieDetailViewModel(
            repository = repository,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `Given composable activate the viewModel When colleting DetailMovie remote Then Result success with the MovieDetail`(){
        runTest {

            val movieDetail = MovieDetail(
                id = 1,
                title = "title1",
                overview = "overview1",
                imagePoster = "Image1",
                imageBanner = "Image1"
            )

            whenever(repository.getMovieDetail("1")).thenReturn(Result.success(movieDetail))

            underTest.uiMovieDetail.test {
                underTest.fetchDetailMovie("1")

                val expected = DetailUiState(movie = movieDetail.toMovieUiData())

                assertEquals(expected, expectMostRecentItem())
            }
        }
    }

    @Test
    fun `Given composable activate the ViewModel and no connection with internet When collecting DetailMovie remote Then Result Falirure with the corresponding exception`(){
        runTest {

            val result = Result.failure<MovieDetail>(UnknownHostException())

            whenever(repository.getMovieDetail("1")).thenReturn(result)

            underTest.uiMovieDetail.test {
                underTest.fetchDetailMovie("1")

                val expected = DetailUiState(isError = true, errorMessage = "No connection")

                assertEquals(expected, expectMostRecentItem())
            }
        }
    }

    @Test
    fun `Given composable activate the ViewModel and have a server error When collecting DetailMovie remote Then Result Falirure with the corresponding exception`(){
        runTest {

            val result = Result.failure<MovieDetail>(NetworkErrorException())

            whenever(repository.getMovieDetail("1")).thenReturn(result)

            underTest.uiMovieDetail.test {
                underTest.fetchDetailMovie("1")

                val expected = DetailUiState(isError = true, errorMessage = "Server Error")

                assertEquals(expected, expectMostRecentItem())
            }
        }
    }

    @Test
    fun `Given composable activate the ViewModel and have other error When collecting DetailMovie remote Then Result Falirure with the corresponding exception`(){
        runTest {

            val result = Result.failure<MovieDetail>(Exception())

            whenever(repository.getMovieDetail("1")).thenReturn(result)

            underTest.uiMovieDetail.test {
                underTest.fetchDetailMovie("1")

                val expected = DetailUiState(isError = true)

                assertEquals(expected, expectMostRecentItem())
            }
        }
    }
}