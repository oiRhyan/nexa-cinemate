package com.nexa.cinemate.screens.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FilmCard(image : String, onClick : (Unit) -> Unit = {}, modifier: Modifier? = null) {

    val imageURL = "https://image.tmdb.org/t/p/w500$image"

    Card(
        onClick = {
            onClick.invoke(Unit)
        },
        modifier = Modifier.height(200.dp).width(120.dp).padding(5.dp).testTag("filme"),
        shape = RoundedCornerShape(15.dp)
    ) {
        GlideImage(
            model = imageURL,
            contentDescription = "Movie & Serie",
            contentScale = ContentScale.Crop
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FilmBannerCard(item : String) {

    val imageURL = "https://image.tmdb.org/t/p/w500$item"

    Card(
        onClick = {

        },
        modifier = Modifier.height(130.dp).width(220.dp).padding(5.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        GlideImage(
            model = imageURL,
            contentDescription = "Movie & Serie",
            contentScale = ContentScale.Crop
        )
    }
}