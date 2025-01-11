package com.example.tipplify.model

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: Int,
    val name: String,
    val ingredients: List<String>,
    val description: String
)

@Serializable
data class RecipeList(val recipes: List<Recipe>)