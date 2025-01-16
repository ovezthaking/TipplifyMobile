package com.example.tipplify

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.tipplify.model.RecipeViewModel
import java.io.File

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RecipeScreen(recipeId: Int, viewModel: RecipeViewModel) {
    val recipe = viewModel.recipes.value.find { it.id == recipeId }
    val context = LocalContext.current

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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {



            recipe?.let {

                Spacer(modifier = Modifier.height(40.dp))

                Text(it.name, color = Color(0xFFffffff), fontSize = 30.sp ,fontWeight = FontWeight.Bold )

                Spacer(modifier = Modifier.height(16.dp))

                if (!it.photoPath.isNullOrEmpty()) {
                    val imagePath = if (it.photoPath.startsWith("recipes/")) {
                        val file = File(context.filesDir, it.photoPath)
                        file.absolutePath
                    } else {
                        "file:///android_asset/${it.photoPath}"
                    }
                    AsyncImage(
                        modifier = Modifier.height(200.dp).width(200.dp),
                        model = imagePath,
                        contentDescription = "Zdjęcie produktu"
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))



                Text("Składniki:", color = Color(0xFFffffff))
                it.ingredients.forEach { ingredient ->
                    Text("- $ingredient", color = Color(0xFFffffff))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Instrukcje: ${it.description}", color = Color(0xFFffffff))

            } ?: Text("Przepis nie znaleziony", color = Color(0xFFffffff), modifier = Modifier.padding(top = 100.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeScreenPreview() {
    RecipeScreen(1, RecipeViewModel(Application()))
}


//wyszukiwanie po składnikach!!!!!!! na 5