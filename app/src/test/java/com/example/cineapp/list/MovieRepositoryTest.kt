package com.example.cineapp.list

import android.accounts.NetworkErrorException
import com.example.cineapp.common.data.local.MovieCategory
import com.example.cineapp.common.model.MovieList
import com.example.cineapp.common.repository.MovieRepository
import com.example.cineapp.common.data.remote.datasource.RemoteDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MovieRepositoryTest {

    val local: FakeListLocalDataSourceImpl = FakeListLocalDataSourceImpl()
    val remote: RemoteDataSource = mock()

    val underTest by lazy {
        MovieRepository(
            local,
            remote
        )
    }

    //NowPlaying
    @Test
    fun `Given no internet connection When getting nowPlayingMovies Then return local data`(){
        runTest {

            val localMovieLists = listOf(
                MovieList(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    image = "Image1",
                    category = MovieCategory.NowPlaying.name,
                )
            )

            whenever(remote.getNowPlayingMovies()).thenReturn(Result.failure(NetworkErrorException()))
            local.nowPlaying = localMovieLists

            val result = underTest.getNowPlayingMovies()

            val expected = Result.success(localMovieLists)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet conection and no local data When getting nowPlayingMovies Then return remote result`(){
        runTest {

            val remoteResult = Result.failure<List<MovieList>>(NetworkErrorException("No internet connection"))

            whenever(remote.getNowPlayingMovies()).thenReturn(remoteResult)
            local.nowPlaying = emptyList()

            val result = underTest.getNowPlayingMovies()

            val expected = remoteResult
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given remote success When getting nowPlayingMovies Then update and return local data`(){
        runTest {

            val movieLists = listOf(
                MovieList(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    image = "Image1",
                    category = MovieCategory.NowPlaying.name,
                )
            )

            whenever(remote.getNowPlayingMovies()).thenReturn(Result.success(movieLists))
            local.nowPlaying = movieLists

            val result = underTest.getNowPlayingMovies()

            val expected = Result.success(movieLists)

            assertEquals(expected, result)
            assertEquals(local.updatedMovieLists, movieLists)
        }
    }

    //Popular
    @Test
    fun `Given no internet connection When getting popularMovies Then return local data`(){

        runTest {

            val localMovieLists = listOf(
                MovieList(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    image = "Image1",
                    category = MovieCategory.Popular.name,
                )
            )

            whenever(remote.getPopularMovies()).thenReturn(Result.failure(NetworkErrorException()))
            local.popular = localMovieLists

            val result = underTest.getPopularMovies()

            val expected = Result.success(localMovieLists)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting popularMovies Then return remote result`(){
        runTest {

            val remoteResult = Result.failure<List<MovieList>?>(NetworkErrorException())

            whenever(remote.getPopularMovies()).thenReturn(remoteResult)
            local.popular = emptyList()

            val result = underTest.getPopularMovies()

            val expected = remoteResult
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given remote sucess When getting popularMovies Then update and return local data`(){
        runTest {

            val movieLists = listOf(
                MovieList(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    image = "Image1",
                    category = MovieCategory.Popular.name,
                )
            )

            whenever(remote.getPopularMovies()).thenReturn(Result.success(movieLists))
            local.popular = movieLists

            val result = underTest.getPopularMovies()

            val expected = Result.success(movieLists)
            assertEquals(expected, result)
            assertEquals(local.updatedMovieLists, movieLists)
        }
    }

    //TopRated
    @Test
    fun `Given no internet connection When getting topRatedMovies Then return local data`(){
        runTest {

            val localMovieLists = listOf(
                MovieList(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    image = "Image1",
                    category = MovieCategory.TopRated.name,
                )
            )

            whenever(remote.getTopRatedMovies()).thenReturn(Result.failure(NetworkErrorException()))
            local.topRated = localMovieLists

            val result = underTest.getTopRatedMovies()

            val expected = Result.success(localMovieLists)

            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting topRatedMovies Then return remote result`(){
        runTest {

            val remoteResult = Result.failure<List<MovieList>?>(NetworkErrorException())

            whenever(remote.getTopRatedMovies()).thenReturn(remoteResult)
            local.topRated = emptyList()

            val result = underTest.getTopRatedMovies()

            val expected = remoteResult
            assertEquals(expected, result)

        }
    }

    @Test
    fun `Given remote success When getting topRatedMovies Then update and return local data`(){
        runTest {

            val movieLists = listOf(
                MovieList(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    image = "Image1",
                    category = MovieCategory.TopRated.name,
                )
            )

            whenever(remote.getTopRatedMovies()).thenReturn(Result.success(movieLists))
            local.topRated = movieLists

            val result = underTest.getTopRatedMovies()

            val expected = Result.success(movieLists)

            assertEquals(expected, result)
            assertEquals(local.updatedMovieLists, movieLists)
        }
    }

    //UpComing
    @Test
    fun `Given no internet connection When getting upComingMovies Then result local data`(){
        runTest {

            val localMovieLists = listOf(
                MovieList(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    image = "Image1",
                    category = MovieCategory.Upcoming.name,
                )
            )

            whenever(remote.getUpComingMovies()).thenReturn(Result.failure(NetworkErrorException()))
            local.upComing = localMovieLists

            val result = underTest.getUpComingMovies()

            val expected = Result.success(localMovieLists)

            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet and no local data When getting upComingMovies Then return remote result`(){
        runTest {

            val remoteResult = Result.failure<List<MovieList>?>(NetworkErrorException())

            whenever(remote.getUpComingMovies()).thenReturn(remoteResult)
            local.upComing = emptyList()

            val result = underTest.getUpComingMovies()

            val expected = remoteResult
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given remote success When getting upComingMovies Then update and return local data`(){
        runTest {

            val movieLists = listOf(
                MovieList(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    image = "Image1",
                    category = MovieCategory.Upcoming.name,
                )
            )

            whenever(remote.getUpComingMovies()).thenReturn(Result.success(movieLists))
            local.upComing = movieLists

            val result = underTest.getUpComingMovies()

            val expected = Result.success(movieLists)
            assertEquals(expected, result)
            assertEquals(local.updatedMovieLists, movieLists)
        }
    }
}