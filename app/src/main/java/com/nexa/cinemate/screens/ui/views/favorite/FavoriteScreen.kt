package com.nexa.cinemate.screens.ui.views.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nexa.cinemate.data.models.response.Movie
import com.nexa.cinemate.screens.items.FilmCard
import com.nexa.cinemate.screens.ui.views.home.HomeViewModel

@Composable
fun FavoriteScreen(
    viewModel: HomeViewModel,
    onMediaSelected : (Movie) -> Unit = {},
    navHostController: NavHostController
) {

    val favorites by viewModel.favoriteMovies.collectAsState()

    LaunchedEffect(favorites) {
        viewModel.getFavoriteMovies()
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
                ).padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(25.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = {
                    navHostController.popBackStack()
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xFF626060)
                ),
                modifier = Modifier.size(40.dp).clip(CircleShape),
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Favorito",
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.width(60.dp))
        Text(
            text = "Favoritados",
            modifier = Modifier.fillMaxWidth().padding(start = 25.dp),
            textAlign = TextAlign.Start,
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(20.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 2),
            modifier = Modifier.fillMaxWidth().padding(25.dp),
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            items(favorites) { movie ->
                movie?.poster_path?.let {
                    FilmCard(it, onClick = {
                        onMediaSelected(movie)
                        navHostController.navigate("details")
                    })
                }
            }
        }

    }
}
