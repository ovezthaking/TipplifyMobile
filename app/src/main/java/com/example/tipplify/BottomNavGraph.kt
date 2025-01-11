package com.example.tipplify

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavGraph(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomMenu(navController = navController) }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.WelcomeScreen.route,
            modifier = Modifier.padding(it)
        ) {
            composable(route = Screens.WelcomeScreen.route) {
                WelcomeScreen { navController.navigate(Screens.MainScreen.route) }
            }
            composable(route = Screens.MainScreen.route) {
                MainScreen { navController.navigate(Screens.RecipeScreen.route) }
            }
            composable(route = Screens.AddRecipeScreen.route) {
                AddRecipeScreen { navController.navigate(Screens.MainScreen.route) }
            }
            composable(route = Screens.RecipeScreen.route) {
                RecipeScreen()
            }
        }
    }
}

