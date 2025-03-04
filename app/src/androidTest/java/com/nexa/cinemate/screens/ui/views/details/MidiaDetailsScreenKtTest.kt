package com.nexa.cinemate.screens.ui.views.details

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollTo
import com.nexa.cinemate.data.models.response.Movie
import com.nexa.cinemate.data.models.response.MovieResponse
import com.nexa.cinemate.data.repositories.MovieRepository
import com.nexa.cinemate.data.repositories.SeriesRepository
import com.nexa.cinemate.screens.ui.views.home.HomeViewModel
import com.nexa.cinemate.screens.ui.views.serie.SeriesViewModel
import io.ktor.client.HttpClient
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.assertEquals


class MidiaDetailsScreenKtTest : KoinTest {

    @get : Rule
    val composableRule = createComposeRule()

    private val NetworkService: HttpClient = mockk(relaxed = true)
    private val movieRepository: MovieRepository = mockk(relaxed = true)
    private val serieRepository: SeriesRepository = mockk(relaxed = true)

    private lateinit var movieViewModel : HomeViewModel
    private lateinit var serieViewModel : SeriesViewModel

    private val item : Movie = Movie(
        false,
        title = "Dragon Ball Super",
        media_type = "serie",
        name = "Dragon Ball Super",
        poster_path = "test.com",
        backdrop_path = "test",
        overview = "Tester",
        id = 1
    )

    @Before
    fun setup() {
        stopKoin()

        startKoin {
            modules(module {
                single { NetworkService }
                single { MovieRepository(get(), get()) }
                single { SeriesRepository(get()) }
            })
        }

        movieViewModel = HomeViewModel(movieRepository)
        serieViewModel = SeriesViewModel(serieRepository)


        composableRule.setContent {
            MediaDetailsScreen(
                movieViewModel,
                serieViewModel,
                item = item
            )
        }
    }

    @Test
    fun renderItemOnScreen(): Unit = runTest{
        item.title?.let { composableRule.onNodeWithTag("title").assertTextEquals(it).assertExists() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun recomendedItensOnScreen() : Unit = runTest {

        coEvery { movieRepository.getMovieRecomendations(item.id!!) } returns MovieResponse(
            page = 1,
            results = listOf(item),
            total_results = 1,
            total_pages = 1
        )

        movieViewModel.getRecomendationMovies(item.id!!)

        composableRule.waitForIdle()

        advanceUntilIdle()

        composableRule.waitUntil(timeoutMillis = 5_000) {
            movieViewModel.relateMovies.value.isNotEmpty()
        }

        Log.v("TEST","Filmes recomendados carregados: ${movieViewModel.relateMovies.value}")


        val result = movieViewModel.relateMovies.value
        Log.v("TEST","Filmes recomendados: $result")
        Log.v("ID DO FILME", "${result[0].id}")

        composableRule.waitUntil(timeoutMillis = 10_000) {
            movieViewModel.relateMovies.value.isNotEmpty()
        }


        composableRule.onNodeWithTag("recomended").assertIsDisplayed().performScrollTo()
        composableRule.onNodeWithTag("filme").assertExists()
        assertEquals(item.id, result[0].id)

    }

    @After
    fun tearDown() {
        stopKoin()
    }

}