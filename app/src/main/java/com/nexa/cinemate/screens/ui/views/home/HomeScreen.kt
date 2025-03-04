package com.nexa.cinemate.screens.ui.views.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nexa.cinemate.data.models.response.Movie
import com.nexa.cinemate.screens.items.CarouselItem
import com.nexa.cinemate.screens.items.FilmCard

@Composable
fun HomeScreen(
    navHostController: NavHostController? = null,
    viewModel : HomeViewModel,
    onSearching : (String) -> Unit = {},
    onMediaSelected : (Movie) -> Unit = {},
    searchMedia : List<Movie>
) {

    var isSearchExpanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val categoryList : List<String> = listOf(
        "Ação", "Aventura", "Romance", "Terror"
    )

    LaunchedEffect(Unit) {
        viewModel.getMovies()
    }

    val movies by viewModel.movies.collectAsState()

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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSearchExpanded) {
                BasicTextField(
                    value = searchText,
                    onValueChange = {
                        searchText = it
                        if (it.isEmpty()) viewModel.searchMidias("")
                    },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                        .background(Color(0xFF626060).copy(0.5f), shape = CircleShape)
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .align(Alignment.CenterVertically),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearching(searchText)
                            isSearchExpanded = false
                        }
                    ),
                    textStyle = TextStyle(
                        color = Color.White,
                        textAlign = TextAlign.Start,
                    )
                )
                IconButton(
                    onClick = {
                        isSearchExpanded = false
                        searchText = ""
                        onSearching("")
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Fechar", tint = Color.White)
                }
            } else {
                IconButton(
                    onClick = { isSearchExpanded = true },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0xFF626060)
                    ),
                    modifier = Modifier.size(40.dp).clip(CircleShape),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            if (!isSearchExpanded) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "welcome back",
                        textDecoration = TextDecoration.None,
                        color = Color(0xFF626060)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        "Rhyan Araujo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))

                IconButton(
                    onClick = {
                        navHostController?.navigate("favorite")
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0xFF626060)
                    ),
                    modifier = Modifier.size(40.dp).clip(CircleShape),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Favorito",
                        tint = Color.White
                    )
                }
            }
        }
        if(searchMedia.isNotEmpty()) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(start = 15.dp).height(640.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(20.dp).fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 15.dp)
                ) {
                    items(searchMedia) { movie ->
                        movie.poster_path?.let {
                            FilmCard(it, onClick = {
                                onMediaSelected(movie)
                                navHostController!!.navigate("details")
                            })
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth().padding(start = 25.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Categorias",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(categoryList) { category ->
                        Button(
                            onClick = {},
                            modifier = Modifier.padding(5.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF5D65BC),
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = category,
                                color = Color.White
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                CarouselItem()
            }
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth().padding(start = 25.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Lançamentos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(20.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth().testTag("lazyrow"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(movies) { movie ->
                        movie.poster_path?.let {
                            FilmCard(it, onClick = {
                                onMediaSelected(movie)
                                navHostController!!.navigate("details")
                            }, modifier = Modifier.testTag("filme"))
                        }
                    }
                }
            }
        }
    }
}