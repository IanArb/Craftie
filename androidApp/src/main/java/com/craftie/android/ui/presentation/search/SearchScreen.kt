package com.craftie.android.ui.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SearchScreen() {
    Column(
        Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        Text(text = "Search")
    }
}