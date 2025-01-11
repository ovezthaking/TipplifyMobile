package com.example.tipplify

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tipplify.model.RecipeViewModel

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
                val viewModel = viewModel<RecipeViewModel>()
                MainScreen(onRecipeScreen = { recipeId ->
                    navController.navigate(Screens.RecipeScreen(recipeId).createRoute())
                }, viewModel = viewModel)
            }
            composable(
                route = Screens.RecipeScreen(0).route,
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getString("recipeId")?.toIntOrNull() ?: 0
                val viewModel = viewModel<RecipeViewModel>()
                RecipeScreen(recipeId = recipeId, viewModel = viewModel)
            }
            composable(route = Screens.AddRecipeScreen.route) {
                AddRecipeScreen { navController.navigate(Screens.MainScreen.route) }
            }
        }
    }
}
