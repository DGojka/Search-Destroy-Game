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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.searchanddestroy.navigation.Screen

@Composable
fun PlanningScreen(navController: NavController) {
    val vm = hiltViewModel<PlanningScreenViewModel>()
    Box(
        Modifier
            .fillMaxSize()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Settings(vm)
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            StartGameButton(navController)
        }
    }
}

@Composable
private fun Settings(vm: PlanningScreenViewModel) {
    Column {
        PlantingPasswordLength(vm)
        Spacer(modifier = Modifier.height(20.dp))
        DefusingPasswordLength(vm)
        Spacer(modifier = Modifier.height(20.dp))
        TimeToExplode(vm)
        Spacer(modifier = Modifier.height(20.dp))
        WrongPasswordPenalty(vm)
    }
}

@Composable
private fun PlantingPasswordLength(vm: PlanningScreenViewModel) {
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
                vm.changePlantingPasswordLength(it.toInt())
            })
    }
}

@Composable
private fun DefusingPasswordLength(vm: PlanningScreenViewModel) {
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
                vm.changeDefusingPasswordLength(it.toInt())
            })
    }
}

@Composable
private fun TimeToExplode(vm: PlanningScreenViewModel) {
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
                vm.changeTimeToExplode(it.toInt())
            })
    }
}

@Composable
private fun WrongPasswordPenalty(vm: PlanningScreenViewModel) {
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
                vm.changeWrongPasswordPenalty(it.toInt())
            })
    }
}

@Composable
private fun StartGameButton(navController: NavController) {
    Button(onClick = { navController.navigate(Screen.BombScreen.route) }) {
        Text(text = "START GAME")
    }
}