package com.example.liquidmusic.ui.glass

import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun GlassBox(
    modifier: Modifier = Modifier,
    blurRadius: Float = 40f,
    tint: Color = Color.White.copy(alpha = 0.12f),
    shape: Shape = RoundedCornerShape(16.dp),
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(shape)
            .drawBehind {
                drawRect(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.White.copy(alpha = 0.20f),
                            Color.White.copy(alpha = 0.07f)
                        )
                    )
                )
            }
            .border(
                width = 0.6.dp,
                brush = Brush.verticalGradient(
                    listOf(
                        Color.White.copy(alpha = 0.45f),
                        Color.White.copy(alpha = 0.08f)
                    )
                ),
                shape = shape
            ),
        content = content
    )
}

@Composable
fun GlassContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
    glassContent: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier) {
        content()
        glassContent()
    }
}