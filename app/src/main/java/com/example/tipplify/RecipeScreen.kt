package com.example.tipplify

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipplify.model.RecipeViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RecipeScreen(recipeId: Int, viewModel: RecipeViewModel) {
    val recipe = viewModel.recipes.value.find { it.id == recipeId }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        recipe?.let {
            Text("Nazwa: ${it.name}", color = Color(0xFFffffff))
            Text("Składniki:", color = Color(0xFFffffff))
            it.ingredients.forEach { ingredient ->
                Text("- $ingredient", color = Color(0xFFffffff))
            }
            Text("Instrukcje: ${it.description}", color = Color(0xFFffffff))
        } ?: Text("Przepis nie znaleziony", color = Color(0xFFffffff))
    }
}