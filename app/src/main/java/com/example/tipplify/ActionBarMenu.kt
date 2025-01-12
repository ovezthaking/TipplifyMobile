package com.example.tipplify

import android.content.Context
import android.content.Intent
import android.content.Intent.CATEGORY_BROWSABLE
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionBarMenu(navController: NavHostController){
    val context = LocalContext.current
    var displayMenu by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = Modifier.height(20.dp).background(color = Color.DarkGray.copy(alpha = 0.7f)),
        title = {},
        actions = {
            IconButton(onClick = { displayMenu = !displayMenu }) {
                Icon(Icons.Default.MoreVert, "more")
            }
            DropdownMenu(
                expanded = displayMenu,
                onDismissRequest = { displayMenu = false }
            ){
                DropdownMenuItem(text = { Text(text = "Visit Website") }, onClick = { openWebsite(context) })
            }
        }
    )
}

private fun openWebsite(context: Context){
    val url = "https://github.com/ovezthaking/TipplifyWebsite"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply{
        addCategory(CATEGORY_BROWSABLE)
    }

    context.startActivity(intent)
}