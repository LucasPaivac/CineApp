package com.example.cineapp.list

import android.accounts.NetworkErrorException
import app.cash.turbine.test
import com.example.cineapp.common.data.local.MovieCategory
import com.example.cineapp.common.model.MovieList
import com.example.cineapp.common.utils.NetworkChecker
import com.example.cineapp.common.repository.MovieRepository
import com.example.cineapp.screens.list.model.MovieListUiEvent
import com.example.cineapp.screens.list.MovieListViewModel
import com.example.cineapp.screens.list.model.ListUiState
import com.example.cineapp.screens.list.model.MovieUiData
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.net.UnknownHostException

class MovieListViewModelTest {

    private val repository: MovieRepository = mock()
    private val networkChecker: NetworkChecker = mock()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())


    private val underTest by lazy {
        MovieListViewModel(repository = repository, networkChecker = networkChecker, dispatcher = testDispatcher)
    }

    //NowPlaying
    @Test
    fun `Given fresh viewModel When collecting to nowPlaying Then assert expected value`() {
        runTest {
            //Given
            val movieLists = listOf(
                MovieList(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.NowPlaying.name,
                )
            )
            whenever(repository.getNowPlayingMovies()).thenReturn(Result.success(movieLists))

            //When
            underTest.uiNowPlaying.test {

                //Then assert expected value
                val expected = ListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "title1",
                            overview = "overview1",
                            image = "image1",
                        )
                    )
                )

                assertEquals(expected, awaitItem())

            }
        }
    }

    @Test
    fun `Given fresh viewModel and no connection When getting nowPlaying Then result with internet exception`() {
        runTest {

            whenever(repository.getNowPlayingMovies()).thenReturn(Result.failure(UnknownHostException()))

            val result = underTest.uiNowPlaying.test {
                val expected = ListUiState(isError = true, errorMessage = "No connection")
                assertEquals(expected, awaitItem())
            }

        }
    }

    @Test
    fun `Given fresh viewModel and have a server error When getting nowPlaying Then result with server error exception`() {
        runTest {

            whenever(repository.getNowPlayingMovies()).thenReturn(Result.failure(NetworkErrorException()))

            val result = underTest.uiNowPlaying.test {
                val expected = ListUiState(isError = true, errorMessage = "Server Error")
                assertEquals(expected, awaitItem())
            }


        }
    }

    @Test
    fun `Given fresh viewModel and have a any error When getting nowPlaying Then result with default error exception`() {
        runTest {

            whenever(repository.getNowPlayingMovies()).thenReturn(Result.failure(Exception()))

            val result = underTest.uiNowPlaying.test {
                val expected = ListUiState(isError = true)
                assertEquals(expected, awaitItem())
            }


        }
    }

    //Popular
    @Test
    fun `Given fresh viewModel When collecting to popular Then assert expected value`() {
        runTest {
            val movieLists = listOf(
                MovieList(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.Popular.name,
                )
            )

            whenever(repository.getPopularMovies()).thenReturn(Result.success(movieLists))

            underTest.uiMostPopular.test {

                val expected = ListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "title1",
                            overview = "overview1",
                            image = "image1",
                        )
                    )
                )

                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel and no connection When getting popular Then result with internet exception`() {
        runTest {

            whenever(repository.getPopularMovies()).thenReturn(Result.failure(UnknownHostException()))

            underTest.uiMostPopular.test {
                val expected = ListUiState(isError = true, errorMessage = "No connection")

                assertEquals(expected, awaitItem())
            }


        }
    }

    @Test
    fun `Given fresh viewModel and have a server error When getting popular Then result with server error exception`() {
        runTest {

            whenever(repository.getPopularMovies()).thenReturn(Result.failure(NetworkErrorException()))

            underTest.uiMostPopular.test {
                val expected = ListUiState(isError = true, errorMessage = "Server Error")
                assertEquals(expected, awaitItem())
            }


        }
    }

    @Test
    fun `Given fresh viewModel and have a any error When getting popular Then result with default error exception`() {
        runTest {

            whenever(repository.getPopularMovies()).thenReturn(Result.failure(Exception()))

            underTest.uiMostPopular.test {
                val expected = ListUiState(isError = true)
                assertEquals(expected, awaitItem())
            }


        }
    }

    //TopRated
    @Test
    fun `Given fresh viewModel When collecting to topRated Then assert expected value`() {
        runTest {
            val movieLists = listOf(
                MovieList(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.TopRated.name
                )
            )

            whenever(repository.getTopRatedMovies()).thenReturn(Result.success(movieLists))

            underTest.uiTopRated.test {

                val expected = ListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "title1",
                            overview = "overview1",
                            image = "image1"
                        )
                    )
                )

                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel and no connection When getting topRated Then result with internet exception`() {
        runTest {

            whenever(repository.getTopRatedMovies()).thenReturn(Result.failure(UnknownHostException()))

            underTest.uiTopRated.test {
                val expected = ListUiState(isError = true, errorMessage = "No connection")
                assertEquals(expected, awaitItem())
            }


        }
    }

    @Test
    fun `Given fresh viewModel and have a server error When getting topRated Then result with server error exception`() {
        runTest {

            whenever(repository.getTopRatedMovies()).thenReturn(Result.failure(NetworkErrorException()))

            underTest.uiTopRated.test {
                val expected = ListUiState(isError = true, errorMessage = "Server Error")
                assertEquals(expected, awaitItem())
            }


        }
    }

    @Test
    fun `Given fresh viewModel and have a any error When getting topRated Then result with default error exception`() {
        runTest {

            whenever(repository.getTopRatedMovies()).thenReturn(Result.failure(Exception()))

            underTest.uiTopRated.test {
                val expected = ListUiState(isError = true)
                assertEquals(expected, awaitItem())
            }


        }
    }

    //UpComing
    @Test
    fun `Given fresh viewModel When collecting to upComing Then assert expected value`() {
        runTest {

            val movieLists = listOf(
                MovieList(
                    id = 1,
                    title = "title1",
                    overview = "overview1",
                    image = "image1",
                    category = MovieCategory.Upcoming.name
                )
            )

            whenever(repository.getUpComingMovies()).thenReturn(Result.success(movieLists))

            underTest.uiUpComing.test {

                val expected = ListUiState(
                    list = listOf(
                        MovieUiData(
                            id = 1,
                            title = "title1",
                            overview = "overview1",
                            image = "image1"
                        )
                    )
                )

                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given fresh viewModel and no connection When getting upComing Then result with internet exception`() {
        runTest {

            whenever(repository.getUpComingMovies()).thenReturn(Result.failure(UnknownHostException()))

            underTest.uiUpComing.test {
                val expected = ListUiState(isError = true, errorMessage = "No connection")
                assertEquals(expected, awaitItem())
            }

        }
    }

    @Test
    fun `Given fresh viewModel and have a server error When getting upComing Then result with server error exception`() {
        runTest {

            whenever(repository.getUpComingMovies()).thenReturn(Result.failure(NetworkErrorException()))

            underTest.uiUpComing.test {
                val expected = ListUiState(isError = true, errorMessage = "Server Error")
                assertEquals(expected, awaitItem())
            }


        }
    }

    @Test
    fun `Given fresh viewModel and have a any error When getting upComing Then result with default error exception`() {
        runTest {

            whenever(repository.getUpComingMovies()).thenReturn(Result.failure(Exception()))

            underTest.uiUpComing.test {
                val expected = ListUiState(isError = true)
                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given internet is available When selected movie to Detail then emit event to navigate`(){
        runTest {

            val movieId = 10
            whenever(networkChecker.hasInternet()).thenReturn(true)

            underTest.uiEvent.test {
                underTest.onMovieClicked(movieId)
                val expected = MovieListUiEvent.NavigateToDetails(movieId)
                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given internet is not available When selected movies to Detail then emit event to show snackbar`(){
        runTest {

            val movieId = 10
            whenever(networkChecker.hasInternet()).thenReturn(false)


            underTest.uiEvent.test {
                underTest.onMovieClicked(movieId)
                awaitItem()
                underTest.onSnackbarDismissed()
                underTest.onMovieClicked(movieId)

                val expected = MovieListUiEvent.ShowMessage("No internet connection")
                assertEquals(expected, awaitItem())
            }
        }
    }


}