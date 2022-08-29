package com.test.jokertime.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.test.jokertime.ui.viewModels.TellMeJokeVM

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.JokeScreen.route + "/{category}",
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { entry ->
            JokeScreen(
                navController = navController,
                category = entry.arguments?.getString("category").toString(),
                tellMeJokeVM = hiltViewModel()
            )
        }
    }
}