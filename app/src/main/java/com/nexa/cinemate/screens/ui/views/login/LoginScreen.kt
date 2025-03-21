package com.nexa.cinemate.screens.ui.views.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nexa.cinemate.R

@Composable
fun LoginScreen(
    onLogin : () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    Column(
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
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.background_movies),
                modifier = Modifier
                    .fillMaxSize(),
                contentDescription = "Image Banner Background",
                contentScale = ContentScale.FillHeight,
                alpha = 0.2f
            )


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(200.dp)
                    .align(androidx.compose.ui.Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xFF121945).copy(0.3f),
                                Color(0xFF404786).copy(0.6f),
                                Color(0xFF7982E1).copy(0.5f)
                            )
                        )
                    )
            )

            Box() {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(top = 300.dp),
                    verticalArrangement= Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.app_logo),
                        modifier = Modifier.height(70.dp),
                        contentDescription = "Logo App",
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(Modifier.height(40.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 25.dp, end = 25.dp)
                    ) {
                        Text(
                            text = "Bem-vindo ao Nexa! Aqui você pode conferir os filmes e séries mais recentes já lançados, além de já se preparar para os grandes lançamentos que estão por vir!",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.login_with_google),
                        contentDescription = "Login With Google Button",
                        modifier = Modifier.fillMaxWidth().height(180.dp).padding(horizontal = 80.dp).clickable(
                            indication = null,
                            interactionSource = interactionSource
                        ) {
                            onLogin.invoke()

                        }
                    )
                    Spacer(Modifier.height(5.dp))
                }
            }
        }
    }
}