package com.example.tipplify

sealed class Screens(val route: String) {
    data object WelcomeScreen : Screens("welcome_screen")
    data object MainScreen : Screens("main_screen")
    data object RecipeScreen : Screens("recipe_screen")
    data object AddRecipeScreen : Screens("add_recipe_screen")
}