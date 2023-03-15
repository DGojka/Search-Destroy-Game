package com.example.searchanddestroy.bombscreen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.ceil

@Composable
fun BombScreen(vm: BombScreenViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val state = vm.uiState.collectAsState().value) {
            BombScreenUiState.Init -> {}
            is BombScreenUiState.Loaded -> {
                Planting(password = state.password, vm = vm)
            }
            is BombScreenUiState.Planted -> {
                BombPlanted(
                    password = state.password,
                    vm = vm,
                    totalTime = state.totalSeconds,
                    currentSeconds = state.currentMs,
                    defused = state.defused
                )
            }
            BombScreenUiState.Error -> TODO()
            BombScreenUiState.Exploded -> BombExploded()
        }
    }
}

@Composable
fun Planting(password: String, vm: BombScreenViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        var typedPassword by remember { mutableStateOf("") }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = password, fontSize = 16.sp)
            Spacer(Modifier.height(80.dp))
            TypePassword(onPasswordEntered = { typedPassword = it })
            Spacer(Modifier.height(24.dp))
            Button(onClick = { vm.startPlanting(typedPassword) }) {
                Text(text = "PLANT")
            }
        }
    }
}

@Composable
fun BombPlanted(
    password: String,
    vm: BombScreenViewModel,
    totalTime: Int,
    currentSeconds: Float,
    defused: Boolean
) {
    Box {
        var typedPassword by remember { mutableStateOf("") }
        if (!defused) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = password, fontSize = 16.sp)
                Spacer(Modifier.height(80.dp))
                TimerComposable(
                    modifier = Modifier.size(300.dp),
                    totalSeconds = totalTime.toFloat() * 1000,
                    currentSeconds = currentSeconds
                )
                TypePassword(onPasswordEntered = { typedPassword = it })
                Spacer(Modifier.height(24.dp))
                Button(onClick = { vm.startDefusing(typedPassword) }) {
                    Text(text = "DEFUSE")
                }
            }
        } else {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "BOMB SUCCESFULLY DEFUSED", fontSize = 16.sp)
                Spacer(Modifier.height(80.dp))
                TimerComposable(
                    modifier = Modifier.size(300.dp),
                    totalSeconds = totalTime.toFloat() * 1000,
                    currentSeconds = currentSeconds
                )
            }
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TimerComposable(
    modifier: Modifier = Modifier,
    totalSeconds: Float,
    currentSeconds: Float,
    strokeWidth: Dp = 10.dp
) {
    fun toCounterText(seconds: Int): String {
        return if (seconds <= 59) {
            seconds.toString()
        } else {
            val minutes = seconds / 60
            val s = seconds % 60
            val secStr = if (s <= 9) {
                "0$s"
            } else {
                s
            }
            "$minutes:$secStr"
        }
    }

    val percentageOfTimer = (currentSeconds / totalSeconds)
    val sweepAngle = 250f

    Box(contentAlignment = Alignment.Center)
    {
        Canvas(modifier = modifier) {
            drawArc(
                color = Color.Red,
                startAngle = -215f,
                sweepAngle = sweepAngle * percentageOfTimer,
                useCenter = false,
                size = Size(size.width, size.height),
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        val sec = (ceil((currentSeconds) / 1000)).toInt()

        AnimatedContent(
            targetState = sec.toString(),
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150))
            }
        ) { seconds ->
            Text(
                text = toCounterText(seconds.toInt()),
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}

@Composable
fun BombExploded() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "TERRORIST WINS", fontSize = 72.sp)
    }
}

@Composable
fun TypePassword(onPasswordEntered: (password: String) -> Unit) {
    var typedPassword by remember { mutableStateOf("") }
    TextField(value = typedPassword, onValueChange = {
        typedPassword = it
        onPasswordEntered(typedPassword)
    })
}