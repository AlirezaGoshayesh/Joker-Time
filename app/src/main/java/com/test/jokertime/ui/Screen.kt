package com.test.jokertime.ui

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object JokeScreen : Screen("joke_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
