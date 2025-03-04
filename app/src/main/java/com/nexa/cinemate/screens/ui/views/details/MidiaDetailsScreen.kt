package com.nexa.cinemate.screens.ui.views.details

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.nexa.cinemate.data.models.response.Movie
import com.nexa.cinemate.screens.items.FilmBannerCard
import com.nexa.cinemate.screens.items.FilmCard
import com.nexa.cinemate.screens.ui.views.home.HomeViewModel
import com.nexa.cinemate.screens.ui.views.serie.SeriesViewModel
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MediaDetailsScreen(
    movieViewModel: HomeViewModel,
    seriesViewModel: SeriesViewModel,
    onMediaSelected: (Movie) -> Unit = {},
    item: Movie?,
    navHostController: NavHostController? = null
) {

    val movie = item

    val posterURL = "https://image.tmdb.org/t/p/w500${movie?.poster_path}"
    val bannerURL = "https://image.tmdb.org/t/p/w500${movie?.backdrop_path}"

    val context = LocalContext.current

    val images by movieViewModel.images.collectAsStateWithLifecycle()
    val serieImages by seriesViewModel.serieImages.collectAsStateWithLifecycle()
    val relatedMovies by movieViewModel.relateMovies.collectAsStateWithLifecycle()
    val relatedSeries by movieViewModel.relateSeries.collectAsStateWithLifecycle()
    val details by movieViewModel.details.collectAsStateWithLifecycle()
    val releaseDate by movieViewModel.date.collectAsStateWithLifecycle()


    LaunchedEffect(images, relatedMovies) {
            movie?.id?.let { seriesViewModel.getSerieImage(it) }
            movie?.id?.let { movieViewModel.getRecomendationSeries(it) }
            movie?.id?.let { movieViewModel.getRecomendationMovies(it) }
            movie?.id?.let { movieViewModel.getMidiaMovies(it) }
            movie?.id?.let { movieViewModel.getDetails(it) }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(
            Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFF121945),
                    Color(0xFF404786),
                    Color(0xFF7982E1)
                )
            )
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
          modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .padding(bottom = 20.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    GlideImage(
                        model = bannerURL,
                        contentDescription = "Midia Image Poster",
                        contentScale = ContentScale.Crop
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .align(androidx.compose.ui.Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                            )
                        ).blur(25.dp)
                )
            }
            Box(

            ) {
                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.Start
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(25.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    navHostController!!.popBackStack()
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color(0xFF626060)
                                ),
                                modifier = Modifier.size(40.dp).clip(CircleShape),
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Buscar",
                                    tint = Color.White
                                )
                            }
                            //Spacer(modifier = Modifier.width(250.dp))
                            IconButton(
                                onClick = {
                                    if (movie != null) {
                                        movieViewModel.setFavoriteMovie(movie)
                                        Toast.makeText(context, "Adicionado aos favoritos!", Toast.LENGTH_LONG).show()
                                    }
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color(0xFF626060)
                                ),
                                modifier = Modifier.size(40.dp).clip(CircleShape),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = "Favoritar",
                                    tint = Color.White
                                )
                            }
                        }
                        IconButton(
                            onClick = {
                                navHostController!!.navigate("video")
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color(0xFF626060).copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.size(70.dp).clip(CircleShape),
                        ) {
                            Icon(
                                modifier = Modifier.size(40.dp),
                                imageVector = Icons.Filled.PlayArrow,
                                contentDescription = "Play",
                                tint = Color.White,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Column(
                        modifier = Modifier.fillMaxSize().padding(25.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Card(
                                modifier = Modifier.height(170.dp).width(120.dp).padding(2.dp),
                                shape = RoundedCornerShape(15.dp)
                            ) {
                                GlideImage(
                                    model = posterURL,
                                    contentDescription = "Movie & Serie",
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    movie?.title.takeIf { !it.isNullOrEmpty() } ?: movie?.name.orEmpty(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp,
                                    modifier = Modifier.testTag("title"),
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    "${
                                        if(movie?.release_date?.isNotEmpty() == true) "Filme" else "Série"
                                    } |  ${
                                        if(details.isNotEmpty()) {
                                            details[0].name + ", " + details[1].name
                                        } else {
                                            ""
                                        }
                                    } | $releaseDate",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Column(
                            Modifier.fillMaxWidth().height(400.dp).verticalScroll(
                                    rememberScrollState()
                                    )
                        ){
                            Text(
                                "Imagens",
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(if(movie?.title?.isEmpty() == true) serieImages else images) {
                                    FilmBannerCard("https://image.tmdb.org/t/p/w500${it.file_path}")
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                "Resumo",
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                "${movie?.overview}",
                                fontWeight = FontWeight.Normal,
                                fontSize = 13.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                "Recomendados",
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxWidth().height(300.dp).testTag("recomended")
                            ) {
                                items(if(movie?.title?.isEmpty() == true) relatedSeries else relatedMovies) { movie ->
                                    Log.v("LazyRow", "Renderizando filme: ${movie.id}")
                                    movie.poster_path?.let {
                                        FilmCard(it, onClick = {
                                              onMediaSelected(movie)
                                              navHostController!!.navigate("details")
                                        }, modifier = Modifier.testTag("film_card"))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}