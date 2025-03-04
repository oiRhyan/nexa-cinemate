package com.nexa.cinemate.screens.items

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.nexa.cinemate.data.models.response.Movie
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue


@Composable
fun MovieCarousel(itens: List<Movie>) {

    val pagerState = rememberPagerState(pageCount = { itens.size })

    if (itens.isEmpty()) return

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % itens.size
            pagerState.animateScrollToPage(nextPage, animationSpec = tween(durationMillis = 3000))
        }
    }

    Column(
        modifier = Modifier.height(220.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp),
            pageSpacing = 5.dp
        ) { page ->
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
            val scale = 1f - (pageOffset.absoluteValue * 0.2f)
            val alpha = 1f - (pageOffset.absoluteValue * 0.4f)

            itens[page].let {
                it.title?.let { it1 ->
                    it?.backdrop_path?.let { it2 ->
                        it.overview?.let { it3 ->
                            (if(it1.isEmpty()) it.name else it1)?.let { it4 ->
                                MovieCarouselCard(
                                    imageUrl = it2,
                                    moiveName = it4,
                                    overview = it3,
                                    modifier = Modifier
                                        .scale(scale)
                                        .alpha(alpha)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieCarouselCard(imageUrl: String, moiveName : String, overview : String, modifier: Modifier = Modifier) {

    val imageURLFormated = "https://image.tmdb.org/t/p/w500$imageUrl"
    val textOverview = overview.take(45) + "..."

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(20.dp))
    ) {

        Box(

        ) {
            GlideImage(
                model = imageURLFormated,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp) // Altura do efeito de fade
                    .align(androidx.compose.ui.Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                        )
                    ).blur(25.dp)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = moiveName,
                style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
            )
            Text(
                text = textOverview,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
            )
        }
    }
}
