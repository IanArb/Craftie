package com.craftie.android.presentation.search

import CraftieTheme
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.craftie.android.presentation.discovery.NoResultsCard
import com.craftie.android.presentation.lightGrey
import kotlinx.coroutines.flow.flowOf

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SearchScreen(onClick: (Pair<String, String>) -> Unit) {
    val viewModel = hiltViewModel<SearchFilterViewModel>()
    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        val recentSearches = listOf(
            "Rascals"
        )

        val styles = listOf(
            "Lager",
            "Stout",
            "India Pale Ale",
            "Pilsner",
            "Red Ale",
            "Dunkel"
        )

        val breweries = listOf(
            "Five Lamps",
            "Rascals",
            "McGargles",
            "Wicklow Wolf"
        )

        val textState = remember { mutableStateOf(TextFieldValue()) }

        SearchView(state = textState)

        val text = textState.value.text

        viewModel.queryBeers(flowOf(text))

        when (val state = uiState.value) {
            is SearchFilterUiState.Success -> {
                SearchResults(state.beers) {
                    onClick(it)
                }
            }

            is SearchFilterUiState.Error -> {
                NoResultsCard {

                }
            }

            is SearchFilterUiState.Loading -> {
                CircularProgressIndicator()
            }

            is SearchFilterUiState.Empty -> {
                NoResultsCard {

                }
            }

            is SearchFilterUiState.Idle -> {
                SearchFilters(
                    recentSearches,
                    styles,
                    breweries
                )
            }
        }
    }
}

@Composable
fun SearchFilters(
    recentSearches: List<String>,
    popularStyles: List<String>,
    popularBreweries: List<String>
) {
    LazyColumn(
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp,
            bottom = 80.dp
        )
    ) {
        itemsList("Recent Searches", recentSearches)
        itemsList("Popular Styles", popularStyles)
        itemsList("Popular Breweries", popularBreweries)
    }
}

@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    Card(modifier = Modifier.padding(bottom = 16.dp)) {
        TextField(
            value = state.value,
            onValueChange = {
                state.value = it
            },
            placeholder = {
                Text(
                    text = "Beer, brewery, flavour, type",
                    fontSize = 16.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            state.value =
                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(15.dp)
                                .size(24.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                leadingIconColor = Color.Black,
                trailingIconColor = Color.Black,
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun Header(text: String) {
    Column(modifier = Modifier.padding(
        top = 8.dp,
        bottom = 0.dp,
        start = 16.dp,
        end = 16.dp
    )) {
        Text(
            text,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(Modifier.padding(6.dp))
    }

}

private fun LazyListScope.itemsList(headerText: String, styles: List<String>) {
    item {
        Header(text = headerText)
    }
    items(styles) {
        Column(modifier = Modifier.padding(
            top = 4.dp,
            bottom = 4.dp,
            start = 16.dp,
            end = 16.dp
        )) {
            Text(it, color = Color.Black)
            Spacer(Modifier.padding(4.dp))
            Divider(color = lightGrey)
            Spacer(Modifier.padding(4.dp))
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val recentSearches = listOf(
        "Rascals"
    )

    val styles = listOf(
        "Lager",
        "Stout",
        "India Pale Ale",
        "Pilsner",
        "Red Ale",
        "Dunkel"
    )

    val breweries = listOf(
        "Five Lamps",
        "Rascals",
        "McGargles",
        "Wicklow Wolf"
    )

    CraftieTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Search")
                    }
                )
            }
        ) {
            Column(modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .fillMaxHeight()) {
                val textState = remember { mutableStateOf(TextFieldValue("")) }
                SearchView(state = textState)
                SearchFilters(
                    recentSearches = recentSearches,
                    popularStyles = styles,
                    popularBreweries = breweries)
            }

        }
    }
}