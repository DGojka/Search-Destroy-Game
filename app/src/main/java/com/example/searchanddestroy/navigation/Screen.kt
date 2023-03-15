package com.example.searchanddestroy.navigation

sealed class Screen(val route: String) {
    object PlanningScreen : Screen("planningScreen")
    object BombScreen: Screen("BombScreen")
}