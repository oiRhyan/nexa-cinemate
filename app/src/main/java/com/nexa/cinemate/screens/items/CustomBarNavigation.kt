package com.nexa.cinemate.screens.items

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nexa.cinemate.R
import com.nexa.cinemate.data.utils.DEFAULT_PADDING
import com.nexa.cinemate.data.utils.times
import com.nexa.cinemate.data.utils.transform
import kotlin.math.PI
import kotlin.math.sin

enum class IconType {
    HOME_SMILE,
    USER_SMILE
}

data class IconItem(val iconType: IconType, val painter: Painter)

@RequiresApi(Build.VERSION_CODES.S)
private fun getRenderEffect(): RenderEffect {
    val blurEffect = RenderEffect.createBlurEffect(80f, 80f, Shader.TileMode.DECAL)
    val alphaMatrix = RenderEffect.createColorFilterEffect(
        ColorMatrixColorFilter(
            ColorMatrix(
                floatArrayOf(
                    1f, 0f, 0f, 0f, 0f,
                    0f, 1f, 0f, 0f, 0f,
                    0f, 0f, 1f, 0f, 0f,
                    0f, 0f, 0f, 50f, -5000f
                )
            )
        )
    )
    return RenderEffect.createChainEffect(alphaMatrix, blurEffect)
}

@Composable
fun CustomBottomNavigation(navHostController: NavHostController) {
    val items = listOf(
        IconItem(IconType.HOME_SMILE, painterResource(R.drawable.home_2_fill))
        ,
        IconItem(IconType.USER_SMILE, painterResource(R.drawable.user_fill))
    )

    Box(
        modifier = Modifier
    ) {

        Image(
            painter = painterResource(R.drawable.bottom_navigation),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .alpha(0.4f)
                .height(80.dp)
        )


        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.matchParentSize().padding(end = 45.dp, start = 45.dp)
        ) {
            items.forEach { icon ->
                IconButton(onClick = {
                    if(icon.iconType == IconType.HOME_SMILE) {
                        navHostController.navigate("home")
                    } else {
                        navHostController.navigate("profile")
                    }
                }) {
                    Icon(
                        painter = icon.painter,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }

}

@Composable
fun FabGroup(
    navHostController: NavHostController? = null,
    animationProgress: Float = 0f,
    renderEffect: androidx.compose.ui.graphics.RenderEffect? = null,
    toggleAnimation: () -> Unit = {}
) {
    val isPlaying = remember { mutableStateOf(false) }
    val fabIcon = remember { mutableStateOf(R.drawable.play_circle_fill) }

    Box(
        Modifier
            .fillMaxSize()
            .graphicsLayer { this.renderEffect = renderEffect }
            .padding(bottom = DEFAULT_PADDING.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedFab(
            icon = ImageVector.vectorResource(R.drawable.film_fill),
            modifier = Modifier
                .padding(
                    PaddingValues(
                        bottom = 72.dp,
                        end = 210.dp
                    ) * FastOutSlowInEasing.transform(0f, 0.8f, animationProgress)
                ),
            onClick = {
                navHostController?.navigate("movies")
            },
            opacity = LinearEasing.transform(0.2f, 0.7f, animationProgress)
        )

        AnimatedFab(
            icon = ImageVector.vectorResource(R.drawable.tv_series_svgrepo_com),
            modifier = Modifier.padding(
                PaddingValues(
                    bottom = 88.dp,
                ) * FastOutSlowInEasing.transform(0.1f, 0.9f, animationProgress)
            ),
            onClick = {
                navHostController?.navigate("series")
            },

            opacity = LinearEasing.transform(0.3f, 0.8f, animationProgress)
        )

        AnimatedFab(
            icon = ImageVector.vectorResource(R.drawable.anime),
            modifier = Modifier.padding(
                PaddingValues(
                    bottom = 72.dp,
                    start = 210.dp
                ) * FastOutSlowInEasing.transform(0.2f, 1.0f, animationProgress)
            ),
            onClick = {
                navHostController?.navigate("animes")
            },
            opacity = LinearEasing.transform(0.4f, 0.9f, animationProgress)
        )

        AnimatedFab(
            modifier = Modifier
                .scale(1f - LinearEasing.transform(0.5f, 0.85f, animationProgress)),
        )

        AnimatedFab(
            icon = ImageVector.vectorResource(fabIcon.value),
            modifier = Modifier
                .rotate(
                    225 * FastOutSlowInEasing
                        .transform(0.35f, 0.65f, animationProgress)
                ),
            onClick = {
                isPlaying.value = !isPlaying.value
                fabIcon.value = if (isPlaying.value) R.drawable.baseline_add_circle_outline_24 else R.drawable.play_circle_fill
                toggleAnimation()
            },
            backgroundColor = Color.Transparent
        )
    }
}

@Composable
fun AnimatedFab(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    opacity: Float = 1f,
    backgroundColor: Color = Color(0xFF5D65BC),
    onClick: () -> Unit = {}
) {
    FloatingActionButton(
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
        containerColor = backgroundColor,
        shape = CircleShape,
        modifier = modifier.size(70.dp)
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = Color.White.copy(alpha = opacity),
                modifier = Modifier.size(45.dp).fillMaxSize()
            )
        }
    }
}

@Composable
fun Circle(color: Color, animationProgress: Float) {
    val animationValue = sin(PI * animationProgress).toFloat()

    Box(
        modifier = Modifier
            .padding(DEFAULT_PADDING.dp)
            .size(70.dp)
            .scale(1f)
            .border(
                width = 2.dp,
                color = color.copy(alpha = color.alpha * animationValue),
                shape = CircleShape
            )
    )
}

@Composable
fun CustomBarNavigation(
    navHostController: NavHostController,
    fabAnimationProgress: Float = 0f,
    clickAnimationProgress: Float = 0f,
    toggleAnimation: () -> Unit = { }
) {

    val renderEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        getRenderEffect().asComposeRenderEffect()
    } else {
        null
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        CustomBottomNavigation(navHostController = navHostController)
        Circle(
            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
            animationProgress = 0.5f
        )

        FabGroup(renderEffect = renderEffect, animationProgress = fabAnimationProgress)
        FabGroup(
            renderEffect = null,
            navHostController = navHostController,
            animationProgress = fabAnimationProgress,
            toggleAnimation = toggleAnimation
        )
        Circle(
            color = Color.White,
            animationProgress = clickAnimationProgress
        )
    }
}