package com.example.tipplify.model

import android.app.Application
import androidx.activity.result.launch


import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import kotlin.concurrent.write

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
                val assetFiles = assetManager.list("recipes") ?: emptyArray()
                val filesDir = File(getApplication<Application>().filesDir, "recipes")
                val filesDirFiles = filesDir.listFiles() ?: emptyArray()

                val allFiles = assetFiles.map { "assets/recipes/$it" } + filesDirFiles.map { it.absolutePath }

                allFiles.forEach { filePath ->
                    if (filePath.endsWith(".json")) {
                        try {
                            val jsonString = if (filePath.startsWith("assets/")) {
                                val inputStream = assetManager.open(filePath.removePrefix("assets/"))
                                inputStream.bufferedReader().use { it.readText() }
                            } else {
                                val file = File(filePath)
                                file.bufferedReader().use { it.readText() }
                            }
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

    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            val recipeWithId = recipe.copy(id = nextRecipeId++)
            val updatedRecipes = _recipes.value + recipeWithId
            _recipes.value = updatedRecipes
            _filteredRecipes.value = updatedRecipes
            saveRecipeToJson(recipeWithId)
        }
    }

    private fun saveRecipeToJson(recipe: Recipe) {
        viewModelScope.launch {
            try {
                val fileName = "recipes/recipe_${recipe.id}.json"
                val outputFile = File(getApplication<Application>().filesDir, fileName)
                val outputStream = java.io.FileOutputStream(outputFile)
                val writer = OutputStreamWriter(outputStream)
                val jsonString = Json.encodeToString(recipe)
                writer.use { it.write(jsonString) }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}


