package com.craftie.android.presentation.search

import CraftieTheme
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.craftie.android.presentation.recentsearches.RecentSearchesViewModel
import com.craftie.data.model.RecentSearchDb
import kotlinx.coroutines.flow.flowOf

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SearchScreen(
    onClick: (Pair<String, String>) -> Unit,
    onRecentSearchClick: (Pair<String, String>) -> Unit
) {
    val searchFilterViewModel = hiltViewModel<SearchFilterViewModel>()
    val recentSearchesViewModel = hiltViewModel<RecentSearchesViewModel>()

    recentSearchesViewModel.init()

    val uiState = searchFilterViewModel.uiState.collectAsState()
    val recentSearches = recentSearchesViewModel.recentSearches.collectAsState()

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

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

        searchFilterViewModel.queryBeers(flowOf(text))

        when (val state = uiState.value) {
            is SearchFilterUiState.Success -> {
                SearchResults(state.beers) {
                    recentSearchesViewModel.addRecentSearch(it.first, it.second)
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
                    recentSearches.value,
                    styles,
                    breweries,
                    onClearAllClick = {
                        recentSearchesViewModel.removeAllRecentSearches()
                    },
                    onRecentSearchClick = {
                        onRecentSearchClick(it)
                    }
                )
            }
        }
    }
}

@Composable
fun SearchFilters(
    recentSearches: List<RecentSearchDb>,
    popularStyles: List<String>,
    popularBreweries: List<String>,
    onClearAllClick: () -> Unit,
    onRecentSearchClick: (Pair<String, String>) -> Unit
) {

    LazyColumn(
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp,
            bottom = 80.dp
        )
    ) {
        if (recentSearches.isNotEmpty()) {
            item {
                RecentSearchesHeader {
                    onClearAllClick()
                }
            }
            items(recentSearches) {
                Column(
                    modifier = Modifier.padding(
                        top = 4.dp,
                        bottom = 4.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                ) {
                    Text(
                        it.name, color =
                        Color.Black,
                        modifier = Modifier.clickable {
                            val pair = Pair(it.id, it.name)
                            onRecentSearchClick(pair)
                        }
                    )
                    Spacer(Modifier.padding(4.dp))
                    Divider(color = lightGrey)
                    Spacer(Modifier.padding(4.dp))
                }

            }
        }

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

@Composable
fun RecentSearchesHeader(
    onClearAllClick: () -> Unit
) {
    Row(modifier = Modifier.padding(
        top = 8.dp,
        bottom = 0.dp,
        start = 16.dp,
        end = 16.dp
    )) {
        Text(
            "Recent Searches",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Text(
            modifier = Modifier
                .padding(start = 110.dp)
                .align(Alignment.CenterVertically)
                .clickable {
                    onClearAllClick()
                },
            text = "Clear All",
            fontWeight = FontWeight.Medium,
            color = Color.Blue
        )
    }
    Spacer(Modifier.padding(6.dp))

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

    val recentSearches = listOf(
        RecentSearchDb().apply {
            id = "1"
            name = "Rascals"
        }
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
                    popularBreweries = breweries,
                    onClearAllClick = {},
                    onRecentSearchClick = {}
                )
            }

        }
    }
}