package com.example.tipplify

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBar(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object MainScreen : BottomBar(Screens.MainScreen.route, "Przepisy", Icons.Default.Home)
    data object AddRecipeScreen : BottomBar(Screens.AddRecipeScreen.route, "Dodaj przepis", Icons.Default.AddCircle)
}