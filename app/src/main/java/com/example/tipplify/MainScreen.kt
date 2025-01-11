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
import androidx.compose.material3.Text
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

@Composable
fun MainScreen(onRecipeScreen: (Int) -> Unit, viewModel: RecipeViewModel) {
    val recipes by viewModel.recipes.collectAsState()
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

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .padding(start = 16.dp, top = 150.dp, end = 16.dp),
                    //.height(300.dp),




                //horizontalAlignment = Alignment.CenterHorizontally,
                //verticalArrangement = Arrangement.Center
            ) {
                items(recipes.size) { index ->
                    Text(text = recipes[index].name,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFffffff),
                        modifier = Modifier.clickable { onRecipeScreen(recipes[index].id) }
                            .padding(bottom = 8.dp)
                            .background(color = Color.Gray.copy(alpha = 0.5f))
                            .width(280.dp)
                            //.fillMaxWidth()
                            .defaultMinSize(minHeight = 35.dp)
                            .wrapContentSize(Alignment.Center)
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
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(onRecipeScreen = {}, viewModel = RecipeViewModel())
}