package com.nexa.cinemate.data.utils

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Easing
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp
import com.nexa.cinemate.R

fun Easing.transform(from: Float, to: Float, value: Float): Float {
    return transform(((value - from) * (1f / (to - from))).coerceIn(0f, 1f))
}

operator fun PaddingValues.times(value: Float): PaddingValues = PaddingValues(
    top = calculateTopPadding() * value,
    bottom = calculateBottomPadding() * value,
    start = calculateStartPadding(LayoutDirection.Ltr) * value,
    end = calculateEndPadding(LayoutDirection.Ltr) * value
)

const val DEFAULT_PADDING = 44

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

@RequiresApi(Build.VERSION_CODES.S)
fun getRenderEffect(): RenderEffect {
    val blurEffect = RenderEffect
        .createBlurEffect(80f, 80f, Shader.TileMode.MIRROR)

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

    return RenderEffect
        .createChainEffect(alphaMatrix, blurEffect)
}


data class MovieMock(
    val id : Int,
    val name :  String,
    val desc : String,
    val image : Int
)

val movieList = listOf(
    MovieMock(
        id = 1,
        name = "Homens brancos não sabem enterrar",
        desc = "Assista agora na Nexa",
        image = R.drawable.white_man_cant_jump2
    ),
    MovieMock(
        id = 2,
        name = "Cobra Kai VI",
        desc = "Testemunhe o grande final de cobra kai, agora no Nexa",
        image = R.drawable.cobra_kai_banner
    ),
    MovieMock(
        id = 3,
        name = "Mr.Robot",
        desc = "Vencedor do Oscar de melhor série",
        image = R.drawable.mr_robot
    )
)

const val API_KEY = ""

