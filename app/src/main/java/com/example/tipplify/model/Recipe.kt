package com.example.tipplify.model

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: Int? = 0,
    val name: String,
    val ingredients: List<String>,
    val description: String,
    val photoPath: String? = null
)

@Serializable
data class RecipeList(val recipes: List<Recipe>)