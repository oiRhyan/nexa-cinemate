package com.nexa.cinemate.screens.ui.views

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nexa.cinemate.data.database.services.firebase.GoogleAuthClient
import com.nexa.cinemate.data.utils.UserManager
import com.nexa.cinemate.screens.items.CustomBarNavigation
import com.nexa.cinemate.screens.ui.views.anime.AnimeScreen
import com.nexa.cinemate.screens.ui.views.anime.AnimeViewModel
import com.nexa.cinemate.screens.ui.views.details.MediaDetailsScreen
import com.nexa.cinemate.screens.ui.views.favorite.FavoriteScreen
import com.nexa.cinemate.screens.ui.views.home.HomeScreen
import com.nexa.cinemate.screens.ui.views.home.HomeViewModel
import com.nexa.cinemate.screens.ui.views.login.LoginScreen
import com.nexa.cinemate.screens.ui.views.login.LoginUIState
import com.nexa.cinemate.screens.ui.views.login.LoginViewModel
import com.nexa.cinemate.screens.ui.views.movie.MovieScreen
import com.nexa.cinemate.screens.ui.views.profile.ProfileScreen
import com.nexa.cinemate.screens.ui.views.serie.SeriesScreen
import com.nexa.cinemate.screens.ui.views.serie.SeriesViewModel
import com.nexa.cinemate.screens.ui.views.video.VideoScreen
import org.koin.androidx.compose.koinViewModel
import kotlin.math.log


@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RestrictedApi")
@Composable
fun MainScreen(
     googleAuthClient: GoogleAuthClient,
     onLoginWithIntent : (launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>) -> Unit,
     onLogOut : () -> Unit
) {

    val localContext = LocalContext.current
    val isMenuExtended = remember { mutableStateOf(false) }
    val navHostController = rememberNavController()

    val fabAnimationProgress by animateFloatAsState(
        targetValue = if (isMenuExtended.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        )
    )

    val clickAnimationProgress by animateFloatAsState(
        targetValue = if (isMenuExtended.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearEasing
        )
    )

    Scaffold(
        bottomBar = {
            val currentRoute = navHostController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute != "video" && currentRoute != "login") {
                CustomBarNavigation(
                    navHostController,
                    fabAnimationProgress = fabAnimationProgress,
                    clickAnimationProgress = clickAnimationProgress
                ) {
                    isMenuExtended.value = isMenuExtended.value.not()
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF121945),
                        Color(0xFF404786),
                        Color(0xFF7982E1)
                    )
                )
            )
        ) {

            val movieViewModel : HomeViewModel = koinViewModel()
            val serieViewModel : SeriesViewModel = koinViewModel()
            val animeViewModel : AnimeViewModel = koinViewModel()
            val loginViewModel: LoginViewModel = koinViewModel()

            val medias = movieViewModel.searchMovie.collectAsState()
            val loginState = loginViewModel.loginState.collectAsStateWithLifecycle()

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if(result.resultCode == RESULT_OK) {
                        loginViewModel.signInWithIntent(
                            googleAuthClient = googleAuthClient,
                            intent = result.data ?: return@rememberLauncherForActivityResult
                        )
                    } else {
                        Log.e("Login", "Intent data is null")
                    }
                }
            )

            LaunchedEffect(loginState.value) {
                when (val state = loginState.value) {
                    is LoginUIState.isEmpty -> {
                        navHostController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                    is LoginUIState.Error -> {
                        Toast.makeText(
                            localContext,
                            state.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is LoginUIState.Sucess -> {
                        if(navHostController.currentDestination?.route == "login") {
                            navHostController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                }
            }

            NavHost(navController = navHostController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        onLogin = {
                            onLoginWithIntent(launcher)
                        },
                    )
                }
                 composable("home") {
                     HomeScreen(navHostController, movieViewModel, onSearching = {
                         movieViewModel.searchMidias(it)
                     }, onMediaSelected = { movieViewModel.selectedMovie = it}, searchMedia = medias.value)
                 }
                 composable("details") {
                     MediaDetailsScreen(movieViewModel, seriesViewModel = serieViewModel, onMediaSelected = { movieViewModel.selectedMovie = it}, movieViewModel.selectedMovie, navHostController)
                 }
                 composable("video") {
                      VideoScreen(movieViewModel)
                 }
                composable("movies") {
                      MovieScreen(navHostController, movieViewModel, onSearching = {
                          movieViewModel.searchMidias(it)
                      }, onMediaSelected = { movieViewModel.selectedMovie= it}, medias.value)
                }
                composable("series") {
                      SeriesScreen(serieViewModel, onMediaSelected = { movieViewModel.selectedMovie = it}, onSearching = {
                          movieViewModel.searchMidias(it)
                      }, navHostController, medias.value)
                }
                composable("animes") {
                      AnimeScreen(animeViewModel, onMediaSelected = { movieViewModel.selectedMovie = it}, onSearching = {
                          movieViewModel.searchMidias(it)
                      }, navHostController, medias.value)
                }
                composable("favorite") {
                      FavoriteScreen(movieViewModel, onMediaSelected = {
                          movieViewModel.selectedMovie = it
                      }, navHostController)
                }
                composable("profile") {
                      ProfileScreen(localContext, navHostController, loginUIState = googleAuthClient.getSignedInUser(), onLogOut = {
                          loginViewModel.resetState()
                          onLogOut.invoke()
                      })
                }
            }
        }
    }
}