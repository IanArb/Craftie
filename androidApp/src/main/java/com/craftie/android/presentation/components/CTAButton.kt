package com.craftie.android.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import orange

@Composable
fun CTAButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 12.dp,
                end = 12.dp
            ),
        colors = ButtonDefaults.buttonColors(backgroundColor = orange),
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}