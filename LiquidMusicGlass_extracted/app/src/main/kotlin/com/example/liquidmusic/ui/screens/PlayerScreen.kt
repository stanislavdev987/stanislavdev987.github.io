package com.example.liquidmusic.ui.screens

import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.QueueMusic
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.liquidmusic.ui.components.GlassTinyPill
import com.example.liquidmusic.ui.theme.ExpandedPlayerBackgroundBrush
import com.example.liquidmusic.ui.theme.LiquidAccent
import com.example.liquidmusic.ui.theme.LiquidTextPrimary
import com.example.liquidmusic.ui.theme.LiquidTextSecondary

@Composable
fun ExpandedPlayerOverlay(
    visible: Boolean,
    isPlaying: Boolean,
    trackTitle: String,
    artistName: String,
    onClose: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(220, easing = LinearOutSlowInEasing)) +
                slideInVertically(initialOffsetY = { it / 8 }, animationSpec = tween(420, easing = FastOutSlowInEasing)) +
                scaleIn(initialScale = 0.985f, animationSpec = tween(420, easing = FastOutSlowInEasing)),
        exit = fadeOut(animationSpec = tween(180)) +
                slideOutVertically(targetOffsetY = { it / 10 }, animationSpec = tween(320, easing = FastOutSlowInEasing)) +
                scaleOut(targetScale = 0.99f, animationSpec = tween(320, easing = FastOutSlowInEasing)),
        modifier = Modifier.fillMaxSize()
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(ExpandedPlayerBackgroundBrush)
                .drawBehind {
                    drawCircle(
                        brush = Brush.radialGradient(listOf(Color(0xFF7CF7D0).copy(alpha = 0.22f), Color.Transparent)),
                        radius = size.minDimension * 0.52f,
                        center = Offset(size.width * 0.5f, size.height * 0.18f)
                    )
                    drawCircle(
                        brush = Brush.radialGradient(listOf(Color(0xFF56B8FF).copy(alpha = 0.18f), Color.Transparent)),
                        radius = size.minDimension * 0.58f,
                        center = Offset(size.width * 0.72f, size.height * 0.32f)
                    )
                }
        ) {
            val compact = maxHeight < 760.dp
            val artworkSize = if (compact) maxWidth - 54.dp else maxWidth - 64.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = if (compact) 18.dp else 22.dp, vertical = 12.dp)
            ) {
                Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                Spacer(modifier = Modifier.height(if (compact) 10.dp else 16.dp))
                ExpandedPlayerTopBar(artistName = artistName, onClose = onClose)
                Spacer(modifier = Modifier.height(if (compact) 18.dp else 26.dp))
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    ExpandedArtwork(artworkSize = artworkSize)
                }
                Spacer(modifier = Modifier.height(if (compact) 18.dp else 24.dp))
                Text(
                    text = trackTitle,
                    color = LiquidTextPrimary,
                    style = if (compact) MaterialTheme.typography.headlineSmall else MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = artistName,
                    color = LiquidTextSecondary,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 6.dp)
                )
                Spacer(modifier = Modifier.height(22.dp))
                ExpandedProgressSection()
                Spacer(modifier = Modifier.height(if (compact) 18.dp else 26.dp))
                ExpandedPlayerControls(isPlaying, onPreviousClick, onPlayPauseClick, onNextClick, compact)
                Spacer(modifier = Modifier.weight(1f))
                ExpandedPlayerBottomRow()
                Spacer(modifier = Modifier.height(if (compact) 14.dp else 20.dp))
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
            }
        }
    }
}

@Composable
fun ExpandedPlayerTopBar(artistName: String, onClose: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(40.dp).clip(CircleShape)
                .background(Color.White.copy(alpha = 0.08f))
                .border(1.dp, Color.White.copy(alpha = 0.10f), CircleShape)
                .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClose),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.Close, "Close", tint = LiquidTextPrimary, modifier = Modifier.size(20.dp))
        }
        Column(
            modifier = Modifier.weight(1f).padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("PLAYING FROM", color = LiquidTextSecondary.copy(alpha = 0.85f), style = MaterialTheme.typography.labelSmall)
            Text(artistName, color = LiquidTextPrimary, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Box(
            modifier = Modifier.size(40.dp).clip(CircleShape)
                .background(Color.White.copy(alpha = 0.08f))
                .border(1.dp, Color.White.copy(alpha = 0.10f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Rounded.MoreHoriz, "More", tint = LiquidTextPrimary, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun ExpandedArtwork(artworkSize: Dp) {
    val shape = RoundedCornerShape(26.dp)
    Box(
        modifier = Modifier
            .size(artworkSize)
            .clip(shape)
            .background(Brush.linearGradient(listOf(Color(0xFF8CFFE0), Color(0xFF4AD2FF), Color(0xFF172531), Color(0xFF0F141B))))
            .border(1.dp, Color.White.copy(alpha = 0.14f), shape)
            .drawBehind {
                drawRoundRect(
                    brush = Brush.verticalGradient(listOf(Color.White.copy(alpha = 0.15f), Color.Transparent, Color.Black.copy(alpha = 0.20f))),
                    size = size,
                    cornerRadius = CornerRadius(26.dp.toPx(), 26.dp.toPx())
                )
                drawCircle(
                    brush = Brush.radialGradient(listOf(Color.White.copy(alpha = 0.12f), Color.Transparent)),
                    radius = size.minDimension * 0.22f,
                    center = Offset(size.width * 0.28f, size.height * 0.22f)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.08f),
                    radius = size.minDimension * 0.09f,
                    center = Offset(size.width * 0.34f, size.height * 0.28f),
                    blendMode = BlendMode.Screen
                )
            }
    )
}

@Composable
fun ExpandedProgressSection() {
    Column {
        Box(
            modifier = Modifier.fillMaxWidth().height(5.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(Color.White.copy(alpha = 0.12f))
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(0.42f).height(5.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(Brush.horizontalGradient(listOf(LiquidTextPrimary, LiquidTextPrimary.copy(alpha = 0.94f))))
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("1:24", color = LiquidTextSecondary, style = MaterialTheme.typography.labelMedium)
            Text("3:48", color = LiquidTextSecondary, style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun ExpandedPlayerControls(
    isPlaying: Boolean,
    onPreviousClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    compact: Boolean
) {
    val playSize = if (compact) 70.dp else 78.dp
    val sideSize = if (compact) 54.dp else 60.dp
    val playIconSize = if (compact) 30.dp else 34.dp
    val sideIconSize = if (compact) 24.dp else 28.dp
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExpandedControlButton(Icons.Rounded.SkipPrevious, "Previous", sideSize, sideIconSize, false, onPreviousClick)
        Spacer(modifier = Modifier.width(20.dp))
        ExpandedControlButton(if (isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow, if (isPlaying) "Pause" else "Play", playSize, playIconSize, true, onPlayPauseClick)
        Spacer(modifier = Modifier.width(20.dp))
        ExpandedControlButton(Icons.Rounded.SkipNext, "Next", sideSize, sideIconSize, false, onNextClick)
    }
}

@Composable
fun ExpandedControlButton(
    icon: ImageVector,
    contentDescription: String,
    size: Dp,
    iconSize: Dp,
    highlighted: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.size(size).clip(CircleShape)
            .background(if (highlighted) Color.White.copy(alpha = 0.16f) else Color.White.copy(alpha = 0.08f))
            .border(1.dp, if (highlighted) Color.White.copy(alpha = 0.18f) else Color.White.copy(alpha = 0.10f), CircleShape)
            .drawBehind {
                if (highlighted) drawCircle(
                    brush = Brush.radialGradient(listOf(LiquidAccent.copy(alpha = 0.20f), Color.Transparent)),
                    radius = this.size.minDimension * 0.82f
                )
            }
            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription, tint = LiquidTextPrimary, modifier = Modifier.size(iconSize))
    }
}

@Composable
fun ExpandedPlayerBottomRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlassTinyPill(icon = Icons.Rounded.VolumeUp, text = "Dolby Atmos")
        GlassTinyPill(icon = Icons.Rounded.QueueMusic, text = "Queue")
    }
}