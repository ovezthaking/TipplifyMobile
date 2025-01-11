package com.example.tipplify

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomMenu(navController = navController)},
        content = { BottomNavGraph(navController = navController) }
    )
    NavHost(navController = navController, startDestination = Screens.WelcomeScreen.route) {
        composable(route = Screens.WelcomeScreen.route){
            WelcomeScreen{navController.navigate(Screens.MainScreen.route)}
        }

        composable(route = Screens.MainScreen.route){
            MainScreen{navController.navigate(Screens.RecipeScreen.route)}
        }
    }
}