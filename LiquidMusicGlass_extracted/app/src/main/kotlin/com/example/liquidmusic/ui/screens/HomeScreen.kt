package com.example.liquidmusic.ui.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.liquidmusic.ui.theme.*

// Яркие цвета для тест-карточек — чтобы видеть искажение за барами
private val testColors = listOf(
    Color(0xFFFF3B6B) to Color(0xFFFF8C42),  // розовый → оранжевый
    Color(0xFF3B82F6) to Color(0xFF8B5CF6),  // синий → фиолетовый
    Color(0xFF10B981) to Color(0xFF06B6D4),  // зелёный → голубой
    Color(0xFFF59E0B) to Color(0xFFEF4444),  // жёлтый → красный
    Color(0xFF8B5CF6) to Color(0xFFEC4899),  // фиолетовый → розовый
    Color(0xFF06B6D4) to Color(0xFF10B981),  // голубой → зелёный
    Color(0xFFFF6B35) to Color(0xFFF7C59F),  // оранжевый → персиковый
    Color(0xFF1D4ED8) to Color(0xFF7C3AED),  // тёмно-синий → фиолетовый
    Color(0xFF059669) to Color(0xFFF59E0B),  // изумрудный → янтарный
    Color(0xFFDC2626) to Color(0xFF9333EA),  // красный → пурпурный
)

@Composable
fun DemoContent() {
    // Яркий радужный фон для теста искажений
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0.0f to Color(0xFF1A0533),
                    0.15f to Color(0xFF0D1B8C),
                    0.32f to Color(0xFF0A7A6B),
                    0.50f to Color(0xFF8B1A00),
                    0.68f to Color(0xFF6B0D8B),
                    0.82f to Color(0xFF003D7A),
                    1.0f to Color(0xFF0A2B1A)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.statusBars))

            Text(
                text = "Music",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Тест искажения стекла ↓ листай",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.70f),
                modifier = Modifier.padding(top = 6.dp, bottom = 24.dp)
            )

            testColors.forEachIndexed { i, (c1, c2) ->
                ColorCard(
                    title = "Playlist ${i + 1}",
                    subtitle = "Ambient · Electronic · Night drive",
                    gradientStart = c1,
                    gradientEnd = c2
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Отступ снизу чтобы последние карточки были видны под барами
            Spacer(modifier = Modifier.height(180.dp))
        }
    }
}

@Composable
fun ColorCard(
    title: String,
    subtitle: String,
    gradientStart: Color,
    gradientEnd: Color
) {
    val shape = RoundedCornerShape(22.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(shape)
            .background(
                Brush.horizontalGradient(listOf(gradientStart, gradientEnd))
            )
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Column {
            Text(
                text = title,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                color = Color.White.copy(alpha = 0.75f),
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

// Оставляем для совместимости если где-то используется
@Composable
fun DemoCard(title: String) {
    ColorCard(
        title = title,
        subtitle = "Ambient · Electronic · Night drive",
        gradientStart = Color(0xFF3B82F6),
        gradientEnd = Color(0xFF8B5CF6)
    )
}
