package com.example.liquidmusic.ui.glass

import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import io.github.kyant0.backdrop.BlurEffect
import io.github.kyant0.backdrop.TileMode
import io.github.kyant0.backdrop.drawBackdrop
import io.github.kyant0.backdrop.layerBackdrop
import io.github.kyant0.backdrop.rememberLayerBackdrop

@Composable
fun RefractionGlassBox(
    modifier: Modifier = Modifier,
    refraction: Float = 0.3f,
    shape: Shape = CircleShape,
    tint: Color = Color.White.copy(alpha = 0.08f),
    content: @Composable BoxScope.() -> Unit = {}
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        KyantBlurGlassBox(
            modifier = modifier,
            shape = shape,
            tint = tint,
            content = content
        )
    } else {
        GlassBox(modifier = modifier, tint = tint, shape = shape, content = content)
    }
}

@Composable
fun KyantBlurGlassBox(
    modifier: Modifier = Modifier,
    blurRadius: Float = 32f,
    shape: Shape = CircleShape,
    tint: Color = Color.White.copy(alpha = 0.08f),
    content: @Composable BoxScope.() -> Unit = {}
) {
    val backdrop = rememberLayerBackdrop()

    Box(
        modifier = modifier
            .clip(shape)
            .layerBackdrop(backdrop)
            .border(
                width = 0.8.dp,
                brush = Brush.verticalGradient(
                    0.0f to Color.White.copy(alpha = 0.65f),
                    0.4f to Color.White.copy(alpha = 0.22f),
                    1.0f to Color.White.copy(alpha = 0.05f)
                ),
                shape = shape
            )
    ) {
        // Слой 1: blur фона через правильный Modifier API
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBackdrop(backdrop, BlurEffect(blurRadius, blurRadius, TileMode.Clamp))
                .drawBehind { drawRect(tint) }
        )

        // Слой 2: контент поверх
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}
