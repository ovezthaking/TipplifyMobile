package com.example.tipplify

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tipplify.model.RecipeViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RecipeScreen(recipeId: Int, viewModel: RecipeViewModel) {
    val recipe = viewModel.recipes.value.find { it.id == recipeId }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillHeight,
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillHeight,
                ),

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
                if (!it.photoPath.isNullOrEmpty()) {
                    AsyncImage(
                        model = "file:///android_asset/${it.photoPath}",
                        contentDescription = "Zdjęcie przepisu"
                    )
                }
            } ?: Text("Przepis nie znaleziony", color = Color(0xFFffffff))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeScreenPreview() {
    RecipeScreen(1, RecipeViewModel(Application()))
}