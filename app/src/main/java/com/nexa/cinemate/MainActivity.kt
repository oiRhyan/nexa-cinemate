package com.nexa.cinemate

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.nexa.cinemate.screens.ui.theme.NexaCinemateTheme
import com.nexa.cinemate.screens.ui.views.MainScreen

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                MainScreen()
            }
        }
    }

}