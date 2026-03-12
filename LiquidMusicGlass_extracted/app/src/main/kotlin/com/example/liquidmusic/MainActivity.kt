package com.example.liquidmusic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LibraryMusic
import androidx.compose.material.icons.rounded.Sensors
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.liquidmusic.ui.components.BottomNavItem
import com.example.liquidmusic.ui.components.MiniPlayerReplicaStyle
import com.example.liquidmusic.ui.components.NewAppleMusicBottomBar
import com.example.liquidmusic.ui.screens.DemoContent
import com.example.liquidmusic.ui.screens.ExpandedPlayerOverlay
import com.example.liquidmusic.ui.theme.HomeBackgroundBrush
import com.example.liquidmusic.ui.theme.LiquidAccent
import com.example.liquidmusic.ui.theme.LiquidMusicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiquidMusicTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black) {
                    LiquidMusicHome()
                }
            }
        }
    }
}

@Composable
fun LiquidMusicHome() {
    val items = listOf(
        BottomNavItem("Home", Icons.Rounded.Home),
        BottomNavItem("New", Icons.Rounded.GridView),
        BottomNavItem("Radio", Icons.Rounded.Sensors),
        BottomNavItem("Library", Icons.Rounded.LibraryMusic)
    )
    var selectedIndex by remember { mutableIntStateOf(0) }
    var isPlaying by remember { mutableStateOf(true) }
    var isExpandedPlayer by remember { mutableStateOf(false) }

    val homeScale by animateFloatAsState(
        targetValue = if (isExpandedPlayer) 0.972f else 1f,
        animationSpec = tween(240, easing = FastOutSlowInEasing), label = ""
    )
    val homeAlpha by animateFloatAsState(
        targetValue = if (isExpandedPlayer) 0f else 1f,
        animationSpec = tween(170, easing = LinearOutSlowInEasing), label = ""
    )

    Box(modifier = Modifier.fillMaxSize().background(HomeBackgroundBrush)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .scale(homeScale)
                .alpha(homeAlpha)
                .drawBehind {
                    drawCircle(
                        brush = Brush.radialGradient(
                            listOf(LiquidAccent.copy(alpha = 0.16f), Color.Transparent)
                        ),
                        radius = size.minDimension * 0.42f,
                        center = Offset(size.width * 0.15f, size.height * 0.18f)
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            listOf(Color(0xFF56B8FF).copy(alpha = 0.14f), Color.Transparent)
                        ),
                        radius = size.minDimension * 0.48f,
                        center = Offset(size.width * 0.82f, size.height * 0.28f)
                    )
                }
        ) {
            DemoContent()
            AnimatedVisibility(
                visible = !isExpandedPlayer,
                enter = fadeIn(tween(150)) + scaleIn(
                    initialScale = 0.992f,
                    animationSpec = tween(180, easing = FastOutSlowInEasing)
                ),
                exit = fadeOut(tween(110)) + scaleOut(
                    targetScale = 0.992f,
                    animationSpec = tween(110, easing = FastOutSlowInEasing)
                ),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    MiniPlayerReplicaStyle(
                        trackTitle = "Girl Like Me",
                        artistName = "PinkPantheress",
                        isPlaying = isPlaying,
                        onPlayPauseClick = { isPlaying = !isPlaying },
                        onNextClick = {},
                        onPlayerClick = { isExpandedPlayer = true }
                    )
                    NewAppleMusicBottomBar(
                        items = items,
                        selectedIndex = selectedIndex,
                        onItemClick = { selectedIndex = it }
                    )
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                }
            }
        }

        ExpandedPlayerOverlay(
            visible = isExpandedPlayer,
            isPlaying = isPlaying,
            trackTitle = "Girl Like Me",
            artistName = "PinkPantheress",
            onClose = { isExpandedPlayer = false },
            onPlayPauseClick = { isPlaying = !isPlaying },
            onNextClick = {},
            onPreviousClick = {}
        )
    }
}
