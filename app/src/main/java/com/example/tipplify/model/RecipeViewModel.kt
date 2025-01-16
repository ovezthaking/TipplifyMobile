package com.example.tipplify.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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




class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _filteredRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val filteredRecipes: StateFlow<List<Recipe>> = _filteredRecipes

    private val _availableIngredients = MutableStateFlow<List<String>>(emptyList())
    val availableIngredients: StateFlow<List<String>> = _availableIngredients

    private val _selectedIngredients = MutableStateFlow<List<String>>(emptyList())
    val selectedIngredients: StateFlow<List<String>> = _selectedIngredients

    private var nextRecipeId = 1

    init {
        loadRecipes()
    }


    private fun loadRecipes() {
        viewModelScope.launch {
            val recipeList = mutableListOf<Recipe>()
            val ingredientsSet = mutableSetOf<String>()
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
                            recipe.ingredients.forEach { ingredientsSet.add(it) }
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
            _availableIngredients.value = ingredientsSet.toList()
        }
    }

    fun searchRecipes(query: String) {
        viewModelScope.launch {
            _filteredRecipes.value = _recipes.value.filter { recipe ->
                recipe.name.contains(query, ignoreCase = true) &&
                        (selectedIngredients.value.isEmpty() || selectedIngredients.value.all { ingredient ->
                            recipe.ingredients.contains(ingredient)
                        })
            }
        }
    }

    fun selectIngredient(ingredient: String) {
        viewModelScope.launch {
            val updatedIngredients = _selectedIngredients.value + ingredient
            _selectedIngredients.value = updatedIngredients
            filterRecipesByIngredients()
        }
    }

    fun removeIngredient(ingredient: String) {
        viewModelScope.launch {
            val updatedIngredients = _selectedIngredients.value.filter { it != ingredient }
            _selectedIngredients.value = updatedIngredients
            filterRecipesByIngredients()
        }
    }

    private fun filterRecipesByIngredients() {
        viewModelScope.launch {
            _filteredRecipes.value = _recipes.value.filter { recipe ->
                selectedIngredients.value.all { ingredient ->
                    recipe.ingredients.contains(ingredient)
                }
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
                val outputStream = FileOutputStream(outputFile)
                val writer = OutputStreamWriter(outputStream)
                val jsonString = Json.encodeToString(recipe)
                writer.use { it.write(jsonString) }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}