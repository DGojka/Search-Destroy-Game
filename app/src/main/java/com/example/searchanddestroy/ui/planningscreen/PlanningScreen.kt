package com.example.searchanddestroy.ui.planningscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.searchanddestroy.R
import com.example.searchanddestroy.navigation.Screen
import com.example.searchanddestroy.navigation.Screen.Companion.SETTINGS
import com.google.gson.Gson
import com.google.gson.GsonBuilder

@Composable
fun PlanningScreen(navController: NavController) {
    val vm = hiltViewModel<PlanningScreenViewModel>()
    val gson = GsonBuilder().create()
    Box(
        Modifier
            .fillMaxSize()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Settings(vm)
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            StartGameButton(navController, vm, gson)
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
        Text(text = stringResource(id = R.string.plant_length))
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
        Text(text = stringResource(id = R.string.defusing_length))
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = defusingPasswordLength, onValueChange = {
                defusingPasswordLength = it
                vm.changeDefusingPasswordLength(it.toIntOrNull()?:0)
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
        Text(text = stringResource(id = R.string.time_to_explode))
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = timeToExplodeSeconds, onValueChange = {
                timeToExplodeSeconds = it
                vm.changeTimeToExplode(it.toIntOrNull()?:0)
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
        Text(text = stringResource(id = R.string.wrong_password_penalty))
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = wrongPasswordPenalty, onValueChange = {
                wrongPasswordPenalty = it
                vm.changeWrongPasswordPenalty(it.toIntOrNull()?:0)
            })
    }
}

@Composable
private fun StartGameButton(navController: NavController, vm: PlanningScreenViewModel, gson: Gson) {
    Button(onClick = {
        navController.navigate(
            Screen.BombScreen.route.replace(
                SETTINGS,
                gson.toJson(vm.uiState.value)
            )
        )
    }) {
        Text(text = stringResource(id = R.string.start_game))
    }
}