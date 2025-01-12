package com.example.tipplify

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomMenu(navController = navController) },
        topBar = { ActionBarMenu(navController = navController) }
    ) {
        BottomNavGraph(navController = navController)
    }
}