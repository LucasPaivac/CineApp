package com.example.cineapp.repository

import android.accounts.NetworkErrorException
import com.example.cineapp.common.data.local.MovieCategory
import com.example.cineapp.common.data.local.model.MovieEntity
import com.example.cineapp.screens.list.model.ListMovie
import com.example.cineapp.common.repository.MovieRepository
import com.example.cineapp.common.data.remote.datasource.RemoteDataSource
import com.example.cineapp.common.data.remote.model.CastDTO
import com.example.cineapp.common.data.remote.model.CreditsDTO
import com.example.cineapp.common.data.remote.model.DetailMovieDTO
import com.example.cineapp.common.data.remote.model.ListMovieDTO
import com.example.cineapp.common.utils.toDetailMovie
import com.example.cineapp.screens.detail.model.DetailMovie
import com.example.cineapp.common.utils.toEntity
import com.example.cineapp.common.utils.toMovieList
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
                MovieEntity(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    imagePoster = "Image1",
                    imageBanner = "Image1",
                    category = MovieCategory.NowPlaying.name,
                )
            )

            whenever(remote.getNowPlayingMovies()).thenReturn(Result.failure(NetworkErrorException()))
            local.nowPlaying = localMovieLists

            val result = underTest.getNowPlayingMovies()

            val expected = Result.success(local.nowPlaying.map { it.toMovieList() })
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet conection and no local data When getting nowPlayingMovies Then return remote result`(){
        runTest {

            val remoteResult = Result.failure<List<ListMovieDTO>>(NetworkErrorException("No internet connection"))

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

            val moviesDtos = listOf(
                ListMovieDTO(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    postPath = "Image1",
                    backdropPath = "Image1"
                )
            )

            val moviesEntity = moviesDtos.map { it.toEntity(MovieCategory.NowPlaying) }

            val moviesList = moviesDtos.map { it.toMovieList(MovieCategory.NowPlaying) }

            whenever(remote.getNowPlayingMovies()).thenReturn(Result.success(moviesDtos))
            local.nowPlaying = moviesEntity

            val result = underTest.getNowPlayingMovies()

            val expected = Result.success(moviesList)

            assertEquals(expected, result)
            assertEquals(local.updatedMovieLists, moviesEntity)
        }
    }

    //Popular
    @Test
    fun `Given no internet connection When getting popularMovies Then return local data`(){

        runTest {

            val localMovies = listOf(
                MovieEntity(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    imagePoster = "Image1",
                    imageBanner = "Image1",
                    category = MovieCategory.Popular.name,
                )
            )

            whenever(remote.getPopularMovies()).thenReturn(Result.failure(NetworkErrorException()))
            local.popular = localMovies

            val result = underTest.getPopularMovies()

            val expected = Result.success(localMovies.map { it.toMovieList() })
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting popularMovies Then return remote result`(){
        runTest {

            val remoteResult = Result.failure<List<ListMovieDTO>?>(NetworkErrorException())

            whenever(remote.getPopularMovies()).thenReturn(remoteResult)
            local.popular = emptyList()

            val result = underTest.getPopularMovies()

            val expected = Result.failure<List<ListMovie>>(
                remoteResult.exceptionOrNull() ?: Exception("Unknown Remote Error"))

            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given remote sucess When getting popularMovies Then update and return local data`(){
        runTest {

            val moviesRemote = listOf(
                ListMovieDTO(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    postPath = "Image1",
                    backdropPath = "Image1"
                )
            )

            val moviesLocal = moviesRemote.map { it.toEntity(MovieCategory.Popular) }

            whenever(remote.getPopularMovies()).thenReturn(Result.success(moviesRemote))
            local.popular = moviesLocal

            val result = underTest.getPopularMovies()

            val expected = Result.success(moviesLocal.map { it.toMovieList() })
            assertEquals(expected, result)
            assertEquals(local.updatedMovieLists, moviesLocal)
        }
    }

    //TopRated
    @Test
    fun `Given no internet connection When getting topRatedMovies Then return local data`(){
        runTest {

            val localMovieLists = listOf(
                MovieEntity(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    imagePoster = "Image1",
                    imageBanner = "Image1",
                    category = MovieCategory.TopRated.name,
                )
            )

            whenever(remote.getTopRatedMovies()).thenReturn(Result.failure(NetworkErrorException()))
            local.topRated = localMovieLists

            val result = underTest.getTopRatedMovies()

            val expected = Result.success(localMovieLists.map { it.toMovieList() })

            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection and no local data When getting topRatedMovies Then return remote result`(){
        runTest {

            val remoteResult = Result.failure<List<ListMovieDTO>?>(NetworkErrorException())

            whenever(remote.getTopRatedMovies()).thenReturn(remoteResult)
            local.topRated = emptyList()

            val result = underTest.getTopRatedMovies()

            val expected = Result.failure<List<ListMovie>>(
                remoteResult.exceptionOrNull() ?: Exception("Unknown Remote Error"))

            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given remote success When getting topRatedMovies Then update and return local data`(){
        runTest {

            val moviesRemote = listOf(
                ListMovieDTO(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    postPath = "Image1",
                    backdropPath = "Image1"
                )
            )

            whenever(remote.getTopRatedMovies()).thenReturn(Result.success(moviesRemote))
            local.topRated = moviesRemote.map { it.toEntity(MovieCategory.TopRated) }

            val result = underTest.getTopRatedMovies()

            val expected = Result.success(local.topRated.map { it.toMovieList() })

            assertEquals(expected, result)
            assertEquals(local.updatedMovieLists, local.topRated)
        }
    }

    //UpComing
    @Test
    fun `Given no internet connection When getting upComingMovies Then result local data`(){
        runTest {

            val localMovieLists = listOf(
                MovieEntity(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    imagePoster = "Image1",
                    imageBanner = "Image1",
                    category = MovieCategory.Upcoming.name,
                )
            )

            whenever(remote.getUpComingMovies()).thenReturn(Result.failure(NetworkErrorException()))
            local.upComing = localMovieLists

            val result = underTest.getUpComingMovies()

            val expected = Result.success(localMovieLists.map { it.toMovieList() })

            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet and no local data When getting upComingMovies Then return remote result`(){
        runTest {

            val remoteResult = Result.failure<List<ListMovieDTO>?>(NetworkErrorException())

            whenever(remote.getUpComingMovies()).thenReturn(remoteResult)
            local.upComing = emptyList()

            val result = underTest.getUpComingMovies()

            val expected = Result.failure<List<ListMovie>>(
                remoteResult.exceptionOrNull() ?: Exception("Unknown Remote Error")
            )
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given remote success When getting upComingMovies Then update and return local data`(){
        runTest {

            val moviesRemote = listOf(
                ListMovieDTO(
                    id = 1,
                    title = "Title1",
                    overview = "Overview1",
                    postPath = "Image1",
                    backdropPath = "Image1"
                )
            )

            whenever(remote.getUpComingMovies()).thenReturn(Result.success(moviesRemote))
            local.upComing = moviesRemote.map { it.toEntity(MovieCategory.Upcoming) }

            val result = underTest.getUpComingMovies()

            val expected = Result.success(local.upComing.map { it.toMovieList() })
            assertEquals(expected, result)
            assertEquals(local.updatedMovieLists, local.upComing)
        }
    }

    //Detail
    @Test
    fun`Given remote success When getting Detail Movie by ID Then return a movieDetail remote data`(){
        runTest {

            val remoteMovie = DetailMovieDTO(
                id = 1,
                title = "title1",
                overview = "overview1",
                postPath = "Image1",
                backdropPath = "Image1",
                genres = emptyList(),
                releaseYear = "Teste",
                runtime = 0,
                voteAverage = 0.0,
                cast = CreditsDTO(emptyList()),
            )

            whenever(remote.getMovieDetail("1")).thenReturn(Result.success(remoteMovie))

            val result = underTest.getMovieDetail("1")

            val expected = Result.success(remoteMovie.toDetailMovie())

            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given remote failure When getting Detail Movie by ID Then return a Result exception`(){
        runTest {

            val remoteResult = Result.failure<DetailMovieDTO>(NetworkErrorException())

            whenever(remote.getMovieDetail("1")).thenReturn(remoteResult)

            val result = underTest.getMovieDetail("1")

            val expected = Result.failure<DetailMovie>(
                remoteResult.exceptionOrNull() ?: Exception("Unknown Remote Error")
            )

            assertEquals(expected, result)
        }
    }
}