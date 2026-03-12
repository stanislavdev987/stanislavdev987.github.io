package com.example.liquidmusic.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.liquidmusic.ui.glass.LiquidGlassBox
import com.kyant.backdrop.Backdrop

@Composable
fun MiniPlayerReplicaStyle(
    backdrop: Backdrop,
    trackTitle: String,
    artistName: String,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onPlayerClick: () -> Unit
) {
    val interaction = remember { MutableInteractionSource() }
    val isPressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = ""
    )
    LiquidGlassBox(
        backdrop = backdrop,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .fillMaxWidth()
            .height(60.dp)
            .scale(scale)
            .clickable(
                interactionSource = interaction,
                indication = null,
                onClick = onPlayerClick
            ),
        blurRadius = 8f,
        lensX = 8f,
        lensY = 16f,
        surfaceColor = Color.White.copy(alpha = 0.10f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .drawBehind { drawRect(Color.White.copy(alpha = 0.15f)) }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    trackTitle,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    artistName,
                    color = Color.White.copy(alpha = 0.65f),
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            AppleIconButton(
                icon = if (isPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                onClick = onPlayPauseClick
            )
            Spacer(modifier = Modifier.width(16.dp))
            AppleIconButton(icon = Icons.Rounded.SkipNext, onClick = onNextClick)
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Composable
fun AppleIconButton(icon: ImageVector, onClick: () -> Unit) {
    val interaction = remember { MutableInteractionSource() }
    val isPressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        if (isPressed) 0.85f else 1f,
        spring(stiffness = Spring.StiffnessLow),
        label = ""
    )
    val alpha by animateFloatAsState(if (isPressed) 0.5f else 1f, tween(150), label = "")
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier
            .size(32.dp)
            .scale(scale)
            .alpha(alpha)
            .clickable(interactionSource = interaction, indication = null, onClick = onClick)
    )
}

@Composable
fun NewAppleMusicBottomBar(
    backdrop: Backdrop,
    items: List<BottomNavItem>,
    selectedIndex: Int,
    onItemClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LiquidGlassBox(
            backdrop = backdrop,
            modifier = Modifier
                .weight(1f)
                .height(64.dp),
            blurRadius = 8f,
            lensX = 12f,
            lensY = 24f,
            surfaceColor = Color.White.copy(alpha = 0.10f)
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val tabWidth = maxWidth / items.size
                val indicatorOffset by animateDpAsState(
                    targetValue = (tabWidth * selectedIndex) + 2.dp,
                    animationSpec = spring(
                        dampingRatio = 0.82f,
                        stiffness = Spring.StiffnessLow
                    ),
                    label = ""
                )
                Box(
                    modifier = Modifier
                        .offset(x = indicatorOffset)
                        .align(Alignment.CenterStart)
                        .wrapContentSize(Alignment.Center)
                        .width(tabWidth - 4.dp)
                        .height(58.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .drawBehind { drawRect(Color.White.copy(alpha = 0.14f)) }
                )
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items.forEachIndexed { index, item ->
                        AppleReplicaBottomBarItem(
                            item = item,
                            selected = index == selectedIndex,
                            onClick = { onItemClick(index) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        val si = remember { MutableInteractionSource() }
        val sp by si.collectIsPressedAsState()
        val ss by animateFloatAsState(
            if (sp) 0.88f else 1f,
            spring(stiffness = Spring.StiffnessLow),
            label = ""
        )
        val sa by animateFloatAsState(if (sp) 0.6f else 1f, tween(150), label = "")

        LiquidGlassBox(
            backdrop = backdrop,
            modifier = Modifier
                .size(64.dp)
                .scale(ss)
                .alpha(sa)
                .clickable(interactionSource = si, indication = null, onClick = {}),
            blurRadius = 8f,
            lensX = 12f,
            lensY = 24f,
            surfaceColor = Color.White.copy(alpha = 0.10f)
        ) {
            Icon(
                Icons.Rounded.Search,
                "Search",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun AppleReplicaBottomBarItem(
    item: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interaction = remember { MutableInteractionSource() }
    val isPressed by interaction.collectIsPressedAsState()
    val tintColor by animateColorAsState(
        if (selected) Color(0xFFFA2D48) else Color.White.copy(alpha = 0.65f),
        tween(200),
        label = ""
    )
    val scale by animateFloatAsState(
        if (isPressed) 0.85f else 1f,
        spring(stiffness = Spring.StiffnessLow),
        label = ""
    )
    val alpha by animateFloatAsState(if (isPressed) 0.5f else 1f, tween(150), label = "")
    Box(
        modifier = modifier
            .fillMaxHeight()
            .scale(scale)
            .alpha(alpha)
            .clickable(interactionSource = interaction, indication = null, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(item.icon, item.label, tint = tintColor, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                item.label,
                color = tintColor,
                fontSize = 10.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}