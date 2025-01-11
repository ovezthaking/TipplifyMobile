package com.example.tipplify

sealed class Screens(val route: String) {
    data object WelcomeScreen : Screens("welcome_screen")
    data object MainScreen : Screens("main_screen")
    data class RecipeScreen(val recipeId: Int) : Screens("recipe_screen/{recipeId}") {
        fun createRoute() = "recipe_screen/$recipeId"
    }
    data object AddRecipeScreen : Screens("add_recipe_screen")
}