package com.nexa.cinemate.screens.ui.views.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.nexa.cinemate.R
import com.nexa.cinemate.data.models.response.user.UserDataProvider
import com.nexa.cinemate.data.utils.UserManager
import com.nexa.cinemate.screens.ui.views.login.LoginUIState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileScreen(
    context : Context,
    navHost: NavHostController,
    onLogOut: () -> Unit,
    loginUIState: UserDataProvider?
) {

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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 35.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(160.dp).clip(CircleShape)
            ) {
                GlideImage(
                    model = loginUIState?.photoUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Profile Image"
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = loginUIState?.username.toString(),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    onLogOut.invoke()
                    navHost.navigate("login")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7982E1),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "SAIR",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}