package com.example.searchanddestroy.ui.planningscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.searchanddestroy.navigation.Screen

@Composable
fun PlanningScreen(navController: NavController) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Settings()
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            StartGameButton(navController)
        }
    }
}

@Composable
private fun Settings() {
    Column {
        PlantingPasswordLength()
        Spacer(modifier = Modifier.height(20.dp))
        DefusingPasswordLength()
        Spacer(modifier = Modifier.height(20.dp))
        TimeToExplode()
        Spacer(modifier = Modifier.height(20.dp))
        WrongPasswordPenalty()
    }
}

@Composable
private fun PlantingPasswordLength() {
    var plantingPasswordLength by remember { mutableStateOf("10") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Planting password length: ")
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = plantingPasswordLength, onValueChange = {
                plantingPasswordLength = it
            })
    }
}

@Composable
private fun DefusingPasswordLength() {
    var defusingPasswordLength by remember { mutableStateOf("10") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Defusing password length: ")
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = defusingPasswordLength, onValueChange = {
                defusingPasswordLength = it
            })
    }
}

@Composable
private fun TimeToExplode() {
    var timeToExplodeSeconds by remember { mutableStateOf("180") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Time to explode: ")
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = timeToExplodeSeconds, onValueChange = {
                timeToExplodeSeconds = it
            })
    }
}

@Composable
private fun WrongPasswordPenalty() {
    var wrongPasswordPenalty by remember { mutableStateOf("3") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Wrong password penalty:")
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = wrongPasswordPenalty, onValueChange = {
                wrongPasswordPenalty = it
            })
    }
}

@Composable
private fun StartGameButton(navController: NavController) {
    Button(onClick = { navController.navigate(Screen.BombScreen.route) }) {
        Text(text = "START GAME")
    }
}