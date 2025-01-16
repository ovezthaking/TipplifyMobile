package com.example.tipplify

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomMenu(navController = navController) },
        topBar = { ActionBarMenu() }
    ) {
        BottomNavGraph(navController = navController)
    }
}