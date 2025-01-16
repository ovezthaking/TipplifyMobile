package com.example.tipplify

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val filteredRecipes by viewModel.filteredRecipes.collectAsState()
    var searchText by remember { mutableStateOf("") }
    var showIngredientDialog by remember { mutableStateOf(false) }
    val availableIngredients by viewModel.availableIngredients.collectAsState()
    val selectedIngredients by viewModel.selectedIngredients.collectAsState()
    var showIngredientList by remember { mutableStateOf(false) }
    var selectedIngredientToEdit by remember { mutableStateOf<String?>(null) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background),
                contentScale = ContentScale.FillHeight,
            )
    ){
        Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text("Przepisy", modifier = Modifier.padding(top = 70.dp), fontSize = 30.sp, color = Color(0xFFffffff))
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
                onValueChange = {
                    searchText = it
                    viewModel.searchRecipes(it)
                },
                label = { Text("Wyszukaj przepis...") }
            )
            if (selectedIngredients.isEmpty()) {
                Button(
                    onClick = { showIngredientDialog = true },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Wybierz składnik")
                }
            } else {
                selectedIngredients.forEach { ingredient ->
                    Button(
                        onClick = { selectedIngredientToEdit = ingredient },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(ingredient)
                    }
                }
            }

            if (showIngredientDialog) {
                AlertDialog(
                    onDismissRequest = { showIngredientDialog = false },
                    title = { Text("Wybierz składnik") },
                    text = {
                        LazyColumn {
                            items(availableIngredients) { ingredient ->
                                Text(
                                    text = ingredient,
                                    modifier = Modifier
                                        .clickable {
                                            viewModel.selectIngredient(ingredient)
                                            showIngredientDialog = false
                                            showIngredientList = true
                                        }
                                        .padding(8.dp)
                                )
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showIngredientDialog = false }) {
                            Text("Anuluj")
                        }
                    }
                )
            }
            if (selectedIngredientToEdit != null) {
                AlertDialog(
                    onDismissRequest = { selectedIngredientToEdit = null },
                    title = { Text("Edytuj składnik") },
                    text = {
                        Column {
                            Text(
                                text = "Czy chcesz usunąć składnik ${selectedIngredientToEdit}?",
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.removeIngredient(selectedIngredientToEdit!!)
                            selectedIngredientToEdit = null
                        }) {
                            Text("Usuń")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showIngredientDialog = true
                            selectedIngredientToEdit = null
                        }) {
                            Text("Podmień")
                        }
                    }
                )
            }
            if (showIngredientList && selectedIngredients.isNotEmpty()) {
                Button(
                    onClick = { showIngredientDialog = true },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Wybierz kolejny składnik")
                }
            }
            LazyColumn(
                modifier = Modifier
                    .padding(start = 16.dp, top = 80.dp, end = 16.dp)
                    .heightIn(min = 0.dp, max =350.dp),
            ) {

                items(filteredRecipes.size) { index ->
                    Text(text = filteredRecipes[index].name,
                        textAlign = TextAlign.Center,
                        color = Color(0xFFffffff),
                        modifier = Modifier
                            .clickable { filteredRecipes[index].id?.let { onRecipeScreen(it) } }
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


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen({}, RecipeViewModel(Application()))
}