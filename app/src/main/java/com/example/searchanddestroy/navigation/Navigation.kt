package com.example.searchanddestroy.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.searchanddestroy.ui.bombscreen.ui.BombScreen
import com.example.searchanddestroy.ui.planningscreen.PlanningScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.PlanningScreen.route){
        composable(route = Screen.PlanningScreen.route){
            PlanningScreen(navController)
        }
        composable(route = Screen.BombScreen.route){
            BombScreen()
        }
    }
}