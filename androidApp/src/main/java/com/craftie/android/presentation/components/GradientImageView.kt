package com.craftie.android.presentation.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun Modifier.gradientImageView(): Modifier {
    return this.drawWithCache {
        val gradient = Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Black),
            startY = 0.0f,
            endY = size.width
        )
        onDrawWithContent {
            drawContent()
            drawRect(gradient, blendMode = BlendMode.Multiply)
        }
    }
}