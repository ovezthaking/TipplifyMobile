package com.example.tipplify

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.route
    ) {
        composable(route = Screens.WelcomeScreen.route){ WelcomeScreen {  } }
        composable(route = Screens.MainScreen.route){ MainScreen {  } }
        composable(route = Screens.AddRecipeScreen.route){ AddRecipeScreen {  } }
    }
}

//tutaj wszystko niby dziala ale po chwili nie widac zadnego ekranu
// albo wywalic bottombar albo nie wiem juz