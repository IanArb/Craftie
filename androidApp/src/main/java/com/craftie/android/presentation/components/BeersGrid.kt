package com.craftie.android.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.craftie.data.model.Beer
import com.google.accompanist.coil.rememberCoilPainter

@ExperimentalFoundationApi
@Composable
fun BeersGrid(beers: List<Beer>) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier,
            contentPadding = PaddingValues(12.dp)
        ) {
            items(beers) { beer ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Card(
                        modifier = Modifier.size(120.dp, 150.dp),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Image(
                            painter = rememberCoilPainter(
                                beer.imageUrl,
                                fadeIn = true
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = beer.name,
                        fontSize = 12.sp
                    )

                }
            }
        }
    }
}