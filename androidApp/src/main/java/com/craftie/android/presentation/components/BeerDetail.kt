package com.craftie.android.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.craftie.android.R
import com.craftie.data.model.Beer
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun BeerDetail(beer: Beer, popUp: () -> Unit) {
    val state = rememberCollapsingToolbarScaffoldState()
    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            val isCollapsedState = state.toolbarState.progress == 0.0f
            if (!isCollapsedState) {
                Image(
                    painter = rememberCoilPainter(
                        beer.breweryInfo.brandImageUrl,
                        fadeIn = true
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .pin()
                        .gradientImageView()
                )
            }

            val arrowDrawable = if (!isCollapsedState) painterResource(id = R.drawable.out_arrow_back_white_24) else painterResource(id = R.drawable.outline_arrow_back_24)

            Text(
                text = beer.name.uppercase(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .road(Alignment.CenterStart, Alignment.BottomStart)
                    .padding(60.dp, 16.dp, 16.dp, 16.dp)
                    .width(175.dp),
                color = if (isCollapsedState) Color.Black else Color.White,
                fontSize = 16.sp,
                maxLines = 3
            )

            Image(
                modifier = Modifier
                    .pin()
                    .padding(16.dp)
                    .clickable {
                        popUp()
                    },
                painter = arrowDrawable,
                contentDescription = null
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = beer.description,
                fontSize = 16.sp,
            )
        }
    }
}