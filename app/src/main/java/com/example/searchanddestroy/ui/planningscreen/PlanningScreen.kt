package com.example.searchanddestroy.ui.planningscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
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
import com.example.searchanddestroy.database.DefaultSettings
import com.example.searchanddestroy.database.DefaultSettings.Companion.DEFAULT_DEFUSING_PASSWORD_LENGTH
import com.example.searchanddestroy.database.DefaultSettings.Companion.DEFAULT_PLANTING_PASSWORD_LENGTH
import com.example.searchanddestroy.database.DefaultSettings.Companion.DEFAULT_TIME_TO_EXPLODE
import com.example.searchanddestroy.database.DefaultSettings.Companion.MAX_PASSWORD_LENGTH
import com.example.searchanddestroy.database.DefaultSettings.Companion.MAX_TIME_TO_EXPLODE
import com.example.searchanddestroy.database.DefaultSettings.Companion.MIN_TIME_TO_EXPLODE
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
        Box(modifier = Modifier.align(Alignment.TopCenter)) {
            Card(modifier = Modifier.size(24.dp).clickable { vm.saveSettingsToSlot()  }) {
                Text("1")
            }
        }
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
    var plantingPasswordLength by remember { mutableStateOf(DEFAULT_PLANTING_PASSWORD_LENGTH.toString()) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(id = R.string.plant_length))
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = plantingPasswordLength, onValueChange = {
                if((it.toIntOrNull() ?: 0) <= MAX_PASSWORD_LENGTH){
                    plantingPasswordLength = it
                    vm.changePlantingPasswordLength(it.toIntOrNull() ?: 1)
                }else {
                    plantingPasswordLength = MAX_PASSWORD_LENGTH.toString()
                    vm.changeDefusingPasswordLength(MAX_PASSWORD_LENGTH)
                }

            }, label = { Text(stringResource(id = R.string.max_value) + " $MAX_PASSWORD_LENGTH") })
    }
}

@Composable
private fun DefusingPasswordLength(vm: PlanningScreenViewModel) {
    var defusingPasswordLength by remember { mutableStateOf(DEFAULT_DEFUSING_PASSWORD_LENGTH.toString()) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(id = R.string.defusing_length))
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = defusingPasswordLength, onValueChange = {
                if ((it.toIntOrNull() ?: 0) <= MAX_PASSWORD_LENGTH) {
                    defusingPasswordLength = it
                    vm.changeDefusingPasswordLength(it.toIntOrNull() ?: 1)
                } else {
                    defusingPasswordLength = MAX_PASSWORD_LENGTH.toString()
                    vm.changeDefusingPasswordLength(MAX_PASSWORD_LENGTH)
                }
            }, label = { Text(stringResource(id = R.string.max_value) + " $MAX_PASSWORD_LENGTH") }
        )
    }
}

@Composable
private fun TimeToExplode(vm: PlanningScreenViewModel) {
    var timeToExplodeSeconds by remember { mutableStateOf(DEFAULT_TIME_TO_EXPLODE.toString()) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(id = R.string.time_to_explode))
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = timeToExplodeSeconds, onValueChange = {
                if ((it.toIntOrNull() ?: 0) in MIN_TIME_TO_EXPLODE.. MAX_TIME_TO_EXPLODE) {
                    timeToExplodeSeconds = it
                    vm.changeTimeToExplode(it.toIntOrNull() ?: 1)
                } else if((it.toIntOrNull() ?: 0) < MIN_TIME_TO_EXPLODE){
                    timeToExplodeSeconds = it
                    vm.changeTimeToExplode(MIN_TIME_TO_EXPLODE)
                } else {
                    timeToExplodeSeconds = MAX_TIME_TO_EXPLODE.toString()
                    vm.changeTimeToExplode(MAX_TIME_TO_EXPLODE)
                }
            }, label = { Text(stringResource(id = R.string.max_value) + " $MAX_TIME_TO_EXPLODE") })
    }
}

@Composable
private fun WrongPasswordPenalty(vm: PlanningScreenViewModel) {
    var wrongPasswordPenalty by remember { mutableStateOf(DefaultSettings.DEFAULT_WRONG_PASSWORD_PENALTY_LENGTH.toString()) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(id = R.string.wrong_password_penalty))
        TextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = wrongPasswordPenalty, onValueChange = {
                if ((it.toIntOrNull() ?: 0) <= MAX_TIME_TO_EXPLODE) {
                    wrongPasswordPenalty = it
                    vm.changeWrongPasswordPenalty(it.toIntOrNull() ?: 0)
                } else {
                    wrongPasswordPenalty = MAX_TIME_TO_EXPLODE.toString()
                    vm.changeWrongPasswordPenalty(MAX_TIME_TO_EXPLODE)
                }
            }, label = { Text(stringResource(id = R.string.max_value) + " $MAX_TIME_TO_EXPLODE") })
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
