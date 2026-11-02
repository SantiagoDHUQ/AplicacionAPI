package com.example.aplicacionapi.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Detail : Screen("detail_screen/{movieId}") {
        fun createRoute(movieId: Int) = "detail_screen/$movieId"
    }
}
