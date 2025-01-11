package com.example.tipplify

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipplify.model.RecipeViewModel

@Composable
fun MainScreen(onRecipeScreen: (Int) -> Unit, viewModel: RecipeViewModel) {
    val recipes by viewModel.recipes.collectAsState()
    Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
    Text("Przepisy", modifier = Modifier.padding(top = 40.dp), fontSize = 30.sp)
    }
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(recipes.size) { index ->
                Text(text = recipes[index].name,
                    modifier = Modifier.clickable { onRecipeScreen(recipes[index].id) }
                )
            }
        }
        /*
        recipes.forEach { recipe ->
            Text(text = recipe.name,
                modifier = Modifier.clickable { onRecipeScreen(recipe.id) }
            )
        }
         */
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(onRecipeScreen = {}, viewModel = RecipeViewModel())
}