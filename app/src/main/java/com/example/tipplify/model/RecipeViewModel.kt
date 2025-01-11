package com.example.tipplify.model

import android.app.Application
import androidx.activity.result.launch

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.io.IOException

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _filteredRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val filteredRecipes: StateFlow<List<Recipe>> = _filteredRecipes

    private var nextRecipeId = 1

    init {
        loadRecipes()
    }

    fun refreshRecipes() {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            val recipeList = mutableListOf<Recipe>()
            try {
                val assetManager = getApplication<Application>().assets
                val files = assetManager.list("recipes")
                files?.forEach { filename ->
                    if (filename.endsWith(".json")) {
                        try {
                            val inputStream = assetManager.open("recipes/$filename")
                            val jsonString = inputStream.bufferedReader().use { it.readText() }
                            val recipe = Json.decodeFromString<Recipe>(jsonString)
                            val recipeWithId = recipe.copy(id = nextRecipeId++)
                            recipeList.add(recipeWithId)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            _recipes.value = recipeList
            _filteredRecipes.value = recipeList
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            _filteredRecipes.value = _recipes.value.filter { recipe ->
                recipe.name.contains(query, ignoreCase = true)
            }
        }
    }
}
