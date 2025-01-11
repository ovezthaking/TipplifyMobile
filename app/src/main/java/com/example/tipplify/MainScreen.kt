package com.example.tipplify

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipplify.model.RecipeViewModel
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.tipplify.model.Recipe

@Composable
fun MainScreen(onRecipeScreen: (Int) -> Unit, viewModel: RecipeViewModel) {


    val recipes by viewModel.recipes.collectAsState()
    var searchText by remember { mutableStateOf("") } // Stan dla tekstu wyszukiwania
    val filteredRecipes = searchRecipes(searchText, recipes) // Filtrowanie przepisów

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background), contentScale = ContentScale.FillHeight
            )
    ){
        Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("Przepisy", modifier = Modifier.padding(top = 40.dp), fontSize = 30.sp, color = Color(0xFFffffff))
        }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                colors = OutlinedTextFieldDefaults.colors( unfocusedContainerColor = Color.Black.copy(alpha = 0.5f), unfocusedTextColor = Color.White, unfocusedLabelColor = Color.White.copy(alpha = 0.5f), unfocusedBorderColor = Color.Black.copy(alpha = 0.5f)),
                modifier = Modifier
                    .padding(top = 150.dp)
                    .background(color = Color.Gray.copy(alpha = 0.5f)),
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Wyszukaj przepis...") }
            )

            LazyColumn(
                modifier = Modifier
                    .padding(start = 16.dp, top = 80.dp, end = 16.dp),
            ) {
                items(filteredRecipes.size) { index -> // Użyj filteredRecipes
                    Text(text = filteredRecipes[index].name,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFffffff),
                        modifier = Modifier
                            .clickable { onRecipeScreen(filteredRecipes[index].id) }
                            .padding(bottom = 8.dp)
                            .background(
                                color = Color.Gray.copy(alpha = 0.5f),
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                            )
                            .width(280.dp)
                            .defaultMinSize(minHeight = 35.dp)
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }
        }
    }
}

fun searchRecipes(query: String, recipes: List<Recipe>): List<Recipe> {
    return recipes.filter { recipe ->
        recipe.name.contains(query, ignoreCase = true)
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(onRecipeScreen = {}, viewModel = RecipeViewModel())
}