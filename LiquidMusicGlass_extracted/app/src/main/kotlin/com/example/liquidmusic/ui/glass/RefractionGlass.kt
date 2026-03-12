package com.example.liquidmusic.ui.glass

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.shapes.Capsule

/**
 * Стеклянный элемент с LiquidGlass эффектом от kyant0.
 *
 * Требует, чтобы родительский контейнер имел Modifier.layerBackdrop(backdrop).
 *
 * @param backdrop      создаётся через rememberLayerBackdrop() в родителе
 * @param blurRadius    радиус blur в dp
 * @param lensX         горизонтальное преломление в dp
 * @param lensY         вертикальное преломление в dp
 * @param surfaceColor  тонировка поверхности стекла
 */
@Composable
fun LiquidGlassBox(
    backdrop: Backdrop,
    modifier: Modifier = Modifier,
    blurRadius: Float = 8f,
    lensX: Float = 12f,
    lensY: Float = 24f,
    surfaceColor: Color = Color.White.copy(alpha = 0.10f),
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier.drawBackdrop(
            backdrop = backdrop,
            shape = { Capsule() },
            effects = {
                vibrancy()
                blur(blurRadius.dp.toPx())
                if (lensX > 0f || lensY > 0f) {
                    lens(lensX.dp.toPx(), lensY.dp.toPx())
                }
            },
            onDrawSurface = {
                drawRect(surfaceColor)
            }
        )
    ) {
        content()
    }
}
