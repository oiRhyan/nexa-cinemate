package com.nexa.cinemate

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.nexa.cinemate.data.database.services.firebase.GoogleAuthClient
import com.nexa.cinemate.screens.ui.theme.NexaCinemateTheme
import com.nexa.cinemate.screens.ui.views.MainScreen
import com.nexa.cinemate.screens.ui.views.login.LoginUIState
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val googleAuthClient by lazy {
        GoogleAuthClient(
            context = applicationContext,
            onTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                Color.WHITE
            ),
            navigationBarStyle = SystemBarStyle.dark(
                Color.WHITE
            ),
        )

        setContent {
            NexaCinemateTheme {
                MainScreen(googleAuthClient, onLoginWithIntent = { launcher ->
                    lifecycleScope.launch {
                        val signInSender = googleAuthClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInSender ?: return@launch
                            ).build()
                        )
                    }
                }, onLogOut = {
                    lifecycleScope.launch {
                        googleAuthClient.signOut()
                    }
                })
            }
        }
    }

}