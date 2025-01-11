package com.example.tipplify

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipplify.ui.theme.TipplifyTheme

@Composable
fun WelcomeScreen(onMainScreen: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Witaj w Tipplify!", color = Color(0xFFffffff))
        Text("", color = Color(0xFFffffff), fontSize = 12.sp)
        Text("Znajdź ulubione przepisy", color = Color(0xFFffffff), fontSize = 12.sp, modifier = Modifier.padding(bottom = 3.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onMainScreen, colors = ButtonDefaults.buttonColors(containerColor = Color.Gray) ) {
            Text("Rozpocznij!")
        }
        Text("", color = Color(0xFFffffff))
        Text("", color = Color(0xFFffffff))
        Image(
            painter = painterResource(id = R.drawable.welcometipple),
            contentDescription = "Welcome To Tipplify"
        )

    }

}

@Preview(showBackground = true)
@Composable
fun WelcomePreview(){
    TipplifyTheme {
        WelcomeScreen{}
    }
}