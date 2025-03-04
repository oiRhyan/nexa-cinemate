package com.nexa.cinemate.screens.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.carousel.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nexa.cinemate.data.utils.movieList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselItem() {

    val item = remember { movieList }

    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { item.count() },
        preferredItemWidth = 600.dp,
        itemSpacing = 5.dp,
        contentPadding = PaddingValues(start = 5.dp),
        modifier = Modifier
            .width(425.dp)
            .wrapContentHeight()
    ) { index ->
        val value = item[index]
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .maskClip(shape = MaterialTheme.shapes.extraLarge)
        ) {

            Box() {
                Image(
                    painter = painterResource(id = value.image),
                    contentDescription = "CarImage",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .align(androidx.compose.ui.Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xFF5D65BC).copy(alpha = 0.6f))
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
                    text = value.name,
                    style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                )
                Text(
                    text = value.desc,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                )
            }
        }
    }
}

@Preview(device = "id:pixel_8_pro")
@Composable
fun showCarousel() {
    CarouselItem()
}