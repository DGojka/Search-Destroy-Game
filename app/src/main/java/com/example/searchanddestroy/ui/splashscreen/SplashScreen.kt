package com.example.searchanddestroy.ui.splashscreen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.searchanddestroy.R
import com.example.searchanddestroy.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    var startSplashScreen by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startSplashScreen) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000,
        )
    )

    LaunchedEffect(key1 = true) {
        startSplashScreen = true
        delay(4000)
        navController.popBackStack()
        navController.navigate(Screen.PlanningScreen.route)
    }

    Animate(alphaAnim.value, R.drawable.the_bomb_logo)
}

@Composable
fun Animate(alpha: Float, resId: Int) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Icon(
            painter = painterResource(id = resId),
            contentDescription = "the_bomb_logo",
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha),
        )
    }
}
