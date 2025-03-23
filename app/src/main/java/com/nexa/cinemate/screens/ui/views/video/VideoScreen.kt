package com.nexa.cinemate.screens.ui.views.video

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.webkit.WebResourceResponse
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.nexa.cinemate.screens.ui.views.home.HomeViewModel

@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoScreen(
    viewModel: HomeViewModel
) {

    val movie = viewModel.selectedMovie

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    HideSystemBars()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        mediaPlaybackRequiresUserGesture = false
                        allowFileAccess = true
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        settings.userAgentString = "Mozilla/5.0 (Linux; Android 13; Pixel 7 Pro) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36"
                    }
                    webChromeClient = WebChromeClient()

                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView?, request: android.webkit.WebResourceRequest?): Boolean {
                            request?.url?.let { uri ->
                                val allowedHost = "embedder.net"
                                if (uri.host?.contains(allowedHost) == true) {
                                    view?.loadUrl(uri.toString())
                                }
                            }
                            return true
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                            super.onShowCustomView(view, callback)
                            callback?.onCustomViewHidden()
                        }

                        override fun onHideCustomView() {
                            super.onHideCustomView()
                        }
                    }



                    if(movie?.title == "") {
                        loadUrl()
                    } else {
                        loadUrl()
                    }

                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    val activity = context as? Activity

    DisposableEffect(Unit) {
        activity?.requestedOrientation = orientation
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun HideSystemBars() {
    val context = LocalContext.current
    val activity = context as? Activity

    LaunchedEffect(Unit) {
        activity?.window?.insetsController?.let { controller ->
            controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
