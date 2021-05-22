package com.craftie.android

import CraftieTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.craftie.android.ui.presentation.Screen
import com.craftie.android.ui.presentation.bottomNavigationItems
import com.craftie.android.ui.presentation.discovery.screen.DiscoveryScreen
import com.craftie.android.ui.presentation.home.HomeScreen
import com.craftie.android.ui.presentation.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    CraftieTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = navController.currentBackStackEntryAsState().value?.destination?.route ?: "")
                })
            },
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    bottomNavigationItems.map {
                        BottomNavigationItem(
                            icon = {
                                   Icon(
                                       it.icon,
                                       it.iconContentDescription
                                   )
                            },
                            selected = currentRoute == it.route,
                            onClick = {
                                navController.navigate(it.route) {
                                    popUpTo(navController.graph.startDestinationRoute ?: "") {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) {
            NavHost(navController = navController, startDestination = Screen.HomeScreen.title) {
                composable(Screen.HomeScreen.title) {
                    HomeScreen()
                }
                composable(Screen.DiscoveryScreen.title) {
                    DiscoveryScreen()
                }
                composable(Screen.SearchScreen.title) {
                    SearchScreen()
                }
            }
        }
    }
}


@Preview
@Composable
fun DefaultPreview() {
    CraftieTheme {

    }
}
