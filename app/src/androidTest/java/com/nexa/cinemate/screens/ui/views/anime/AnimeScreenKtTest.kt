package com.nexa.cinemate.screens.ui.views.anime


import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.ViewModel
import com.nexa.cinemate.data.models.response.Movie
import com.nexa.cinemate.data.models.response.MovieResponse
import com.nexa.cinemate.data.repositories.AnimeRepository
import io.ktor.client.HttpClient
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.Test



class AnimeScreenKtTest : KoinTest {

    @get: Rule
    val composableRule = createComposeRule()

    private val NetworkService: HttpClient = mockk(relaxed = true)
    private val repository: AnimeRepository = mockk(relaxed = true)
    private lateinit var viewModel : AnimeViewModel


    private val mockAnimeList = listOf(
        Movie(
            false,
            title = "Dragon Ball Super",
            media_type = "serie",
            name = "Dragon Ball Super",
            overview = "Tester"
        )
    )

    @Before
    fun setup() {

        stopKoin()

        startKoin {
            modules(module {
                single { NetworkService }
                single { AnimeRepository(get()) }
                single { AnimeViewModel(get()) }
            })
        }

        viewModel = AnimeViewModel(repository)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun getAnimesFromRepositoryTest() = runTest {
        coEvery { repository.getAnimes() } returns
                MovieResponse(
                    page = 1,
                    results = mockAnimeList,
                    total_results = 1,
                    total_pages = 1
                )


        val anime = repository.getAnimes()


        assertEquals("Dragon Ball Super", anime.results[0].title)
        assertEquals("Tester", anime.results[0].overview)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAnimesFromViewModelTest() = runTest {
        coEvery { repository.getAnimes() } returns MovieResponse(
            page = 1,
            results = mockAnimeList,
            total_results = 1,
            total_pages = 1
        )

        viewModel.getAnimes()

        advanceUntilIdle()

        composableRule.waitForIdle()

        val result = viewModel.animes.value

        assert(result.isNotEmpty()) { "Lista de animes está vazia!" }
        assertEquals("Dragon Ball Super", result[0].title)
        assertEquals("Tester", result[0].overview)
    }

}