package com.example.searchanddestroy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.searchanddestroy.bombscreen.BombScreen
import com.example.searchanddestroy.bombscreen.BombScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm = BombScreenViewModel()
        setContent {
            BombScreen(vm = vm)
        }
    }
}
