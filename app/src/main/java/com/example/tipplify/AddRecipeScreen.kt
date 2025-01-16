package com.example.tipplify

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipplify.model.Recipe
import com.example.tipplify.model.RecipeViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.UUID
import kotlin.text.split
import kotlin.text.trim


@Composable
fun AddRecipeScreen(onMainScreen: () -> Unit, viewModel: RecipeViewModel) {
    var name by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var photoPath by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            val fileName = copyImageToAssets(context, it)
            photoPath = "recipes/$fileName"
        }
    }

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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                value = name,
                onValueChange = { name = it },
                label = { Text("Nazwa przepisu", color = Color.Gray) },
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .width(300.dp)
            )
            OutlinedTextField(
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Składniki (oddzielone przecinkami)", color = Color.Gray) },
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .width(300.dp)
            )
            OutlinedTextField(
                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
                value = description,
                onValueChange = { description = it },
                label = { Text("Opis", color = Color.Gray) },
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .height(180.dp)
                    .width(300.dp)
            )
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("Wybierz zdjęcie")
            }
            OutlinedTextField(
                value = photoPath,
                onValueChange = { photoPath = it },
                label = { Text("Ścieżka do zdjęcia", color = Color.Gray) },
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .width(300.dp),
                enabled = false
            )
            Button(onClick = {
                val recipe = Recipe(
                    name = name,
                    ingredients = ingredients.split(",").map { it.trim() },
                    description = description,
                    photoPath = photoPath
                )
                viewModel.addRecipe(recipe)
                onMainScreen()
            }) {
                Text("Dodaj przepis")
            }
        }
    }
}

private fun copyImageToAssets(context: Context, uri: Uri): String {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val fileName = UUID.randomUUID().toString() + ".png"
    val outputFile = File(context.filesDir, fileName)
    try {
        inputStream?.use { input ->
            FileOutputStream(outputFile).use { output ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }
        //val assetManager = context.assets
        val assetDir = File(context.filesDir, "recipes")
        if(!assetDir.exists()){
            assetDir.mkdir()
        }
        val assetFile = File(assetDir, fileName)
        outputFile.copyTo(assetFile, overwrite = true)
        outputFile.delete()
        return fileName
    } catch (e: IOException) {
        e.printStackTrace()
        return ""
    }
}


@Preview(showBackground = true)
@Composable
fun AddRecipeScreenPreview() {
    AddRecipeScreen(onMainScreen = {}, viewModel = RecipeViewModel(Application()))
}