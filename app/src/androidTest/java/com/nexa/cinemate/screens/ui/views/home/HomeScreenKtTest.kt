package com.nexa.cinemate.screens.ui.views.home

import android.util.Log
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.nexa.cinemate.data.models.response.Movie
import com.nexa.cinemate.data.models.response.MovieResponse
import com.nexa.cinemate.data.repositories.AnimeRepository
import com.nexa.cinemate.data.repositories.MovieRepository
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


class HomeScreenKtTest : KoinTest {

    @get:Rule
    val composableRule = createComposeRule()

    private val network : HttpClient = mockk(relaxed = true)
    private val repository : MovieRepository = mockk(relaxed = true)
    private lateinit var viewModel : HomeViewModel

    private val movieItem = Movie(
        name = "Velozes e Furiosos",
        adult = false,
        backdrop_path = "test",
        poster_path = "test.com",
        title = "Velozes e Furiosos",
        id = 2,
        overview = "Test"
    )

    private val mockList = listOf(
        movieItem
    )

    @Before
    fun init() {
        stopKoin()

        startKoin {
            modules( module {
                single { network }
                single { AnimeRepository(get()) }
                single { HomeViewModel(get()) }
            })
        }

        viewModel = HomeViewModel(repository)

        composableRule.setContent {
            HomeScreen(
                viewModel = viewModel,
                searchMedia = emptyList()
            )
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun showMoviesOnHomeScreen() = runTest {

        coEvery { repository.getMovies() } returns MovieResponse(
            page = 1,
            results = mockList,
            total_results = 1,
            total_pages = 1
        )

        viewModel.getMovies()

        composableRule.waitForIdle()

        advanceUntilIdle()

        composableRule.waitUntil(timeoutMillis = 5_000) {
            viewModel.movies.value.isNotEmpty()
        }

        val response = viewModel.movies.value

        Log.v("Test Movie", "${response}")

        composableRule.waitUntil(timeoutMillis = 1200_000) {
            viewModel.movies.value.isNotEmpty()
        }

        composableRule.onNodeWithTag("lazyrow").assertIsDisplayed()
        composableRule.onNodeWithTag("filme").assertExists()
        assertEquals(movieItem.title, response[0].title)

    }

    @After
    fun tearDown() {
        stopKoin()
    }

}