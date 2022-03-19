package com.craftie.android.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.craftie.android.R

@Composable
fun NoInternetConnectionCard(callback: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_wifi_black_48dp),
                    contentDescription = null
                )
                Spacer()
                Text(
                    text = "No Internet Connection",
                    fontWeight = FontWeight.Bold
                )
                Spacer()
                Text("Please make sure you are connected to a mobile or WIFI network and try again.")
                Spacer()
                TryAgainButton(callback)
            }

        }

    }
}

@Composable
fun NoResultsCard(callback: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_error_outline_black_24dp),
                    contentDescription = null
                )
                Spacer()
                Text(
                    text = "No Results Found",
                    fontWeight = FontWeight.Bold
                )
                Spacer()
                Text("There is no results available at this time. Please try again")
                Spacer()
                TryAgainButton(callback)
            }
        }
    }
}

@Composable
fun EmptyResultCard(title: String, message: String) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(modifier = Modifier.padding(16.dp)) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer()
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )
                Spacer()
                Text(message)
                Spacer()
            }
        }
    }
}

@Composable
fun TryAgainButton(callback: () -> Unit) {
    Button(onClick = {
        callback()
    }) {
        Text("TRY AGAIN")
    }
}

@Composable
fun Spacer() {
    Spacer(modifier = Modifier.padding(12.dp))
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    LazyColumn {
        item {
            NoResultsCard {

            }
            Spacer(modifier = Modifier.padding(10.dp))
            EmptyResultCard(
                title = "No Results Found",
                message = "There is no results available for that search. Please try another search."
            )
            Spacer(modifier = Modifier.padding(10.dp))
            NoInternetConnectionCard {

            }
        }
    }
}