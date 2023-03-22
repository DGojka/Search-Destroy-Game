package com.example.searchanddestroy.ui.planningscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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
import com.example.searchanddestroy.ui.theme.WhiteSmoke
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
            SaveSlotList(vm = vm)
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
private fun SaveSlotList(vm: PlanningScreenViewModel) {
    with(vm.uiState.collectAsState().value.saveSlots) {
        Row(Modifier) {
            SaveSlot(
                vm = vm,
                slotId = 1,
                slotName = getOrNull(0)?.name
                    ?: "Empty save slot"
            )
            SaveSlot(
                vm = vm,
                slotId = 2,
                slotName = getOrNull(1)?.name
                    ?: "Empty save slot"
            )
            SaveSlot(
                vm = vm,
                slotId = 3,
                slotName = getOrNull(2)?.name
                    ?: "Empty save slot"
            )
            SaveSlot(
                vm = vm,
                slotId = 4,
                slotName = getOrNull(3)?.name
                    ?: "Empty save slot"
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SaveSlot(vm: PlanningScreenViewModel, slotId: Int, slotName: String) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            SaveSlotDialog(
                onDismiss = { showDialog = false },
                slotId = slotId,
                isSlotInitialized = vm.uiState.collectAsState().value.saveSlots.getOrNull(slotId) != null,
                slotName = slotName,
                vm = vm
            )
        }
    }
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .size(88.dp)
            .padding(6.dp)
            .combinedClickable(onClick = {
                showDialog = true
            },
                onLongClick = { Log.e("asd", "asd") }, onDoubleClick = {
                    Log.e("asd", "asd")
                })
    ) {
        Box(
            Modifier
                .padding(12.dp)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(slotName)
        }
    }
}

@Composable
private fun SaveSlotDialog(
    slotId: Int,
    slotName: String,
    isSlotInitialized: Boolean,
    onDismiss: () -> Unit,
    vm: PlanningScreenViewModel
) {
    var newSlotName: String by remember { mutableStateOf(slotName) }
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(16.dp), Arrangement.Center) {
            ExerciseNameControls(
                currentName = if (isSlotInitialized) slotName else "",
                onNameEntered = { newSlotName = it })
            Spacer(modifier = Modifier.height(12.dp))
            if (isSlotInitialized) {
                Button(onClick = {
                    vm.saveSettingsToSlot(slotId, newSlotName)
                    onDismiss()
                }) {
                    Text("Override current settings")
                    //
                }
                Button(onClick = {
                    //TODO import
                    onDismiss()
                }) {
                    Text("Import settings")
                }
            } else {
                Button(onClick = {
                    vm.saveSettingsToSlot(slotId, newSlotName)
                    onDismiss()
                }) {
                    Text("Save settings")
                }
            }
        }
    }
}

@Composable
fun ExerciseNameControls(
    currentName: String,
    onNameEntered: (value: String) -> Unit
) {
    var exerciseName: String by remember { mutableStateOf(currentName) }
    val context = LocalContext.current
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
        text = "Slot name",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
    Box(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(percent = 10),
                color = Color(WhiteSmoke.toArgb()),
            )
            .height(36.dp)
            .padding(start = 12.dp, end = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            value = exerciseName,
            textStyle = TextStyle(fontSize = 16.sp),
            singleLine = true,
            onValueChange = {
                if (it.length < 20) {
                    exerciseName = it
                    onNameEntered(it)
                } else {
                    Toast.makeText(
                        context,
                        "Slot name can be up to 20 characters long",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
        )
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
                if ((it.toIntOrNull() ?: 0) <= MAX_PASSWORD_LENGTH) {
                    plantingPasswordLength = it
                    vm.changePlantingPasswordLength(it.toIntOrNull() ?: 1)
                } else {
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
                if ((it.toIntOrNull() ?: 0) in MIN_TIME_TO_EXPLODE..MAX_TIME_TO_EXPLODE) {
                    timeToExplodeSeconds = it
                    vm.changeTimeToExplode(it.toIntOrNull() ?: 1)
                } else if ((it.toIntOrNull() ?: 0) < MIN_TIME_TO_EXPLODE) {
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
