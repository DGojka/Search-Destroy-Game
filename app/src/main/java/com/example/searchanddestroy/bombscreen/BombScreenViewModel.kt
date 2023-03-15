package com.example.searchanddestroy.bombscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BombScreenViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow<BombScreenUiState>(BombScreenUiState.Init)
    val uiState: StateFlow<BombScreenUiState> = _uiState
    private var timer: Timer = Timer()
    private val LOG_TAG = BombScreenViewModel::class.qualifiedName

    init {
        viewModelScope.launch {
            _uiState.emit(BombScreenUiState.Loaded(generateRandomString()))
        }
    }

    fun startPlanting(password: String) {
        if (doesPasswordMatch(password)) {
            viewModelScope.launch {
                _uiState.emit(
                    BombScreenUiState.Planted(
                        generateRandomString(),
                        TIMER_TOTAL_SECONDS,
                        TIMER_TOTAL_SECONDS.toFloat()
                    )
                )
            }
            plantBomb()
        } else {
            viewModelScope.launch {
                Log.i(LOG_TAG,"WRONG PASSWORD PLANTING")
                _uiState.emit(BombScreenUiState.Loaded(generateRandomString()))
            }
        }
    }

    fun startDefusing(password: String) {
        val statePlanted = _uiState.value as BombScreenUiState.Planted
        if (doesPasswordMatch(password = password)) {
            Log.i(LOG_TAG,"BOMB DEFUSED")
            timer.pause()
            _uiState.value = statePlanted.copy(defused = true)
        } else {
            Log.i(LOG_TAG,"WRONG PASSWORD DEFUSING")
            _uiState.value = statePlanted.copy(password = generateRandomString())
        }
    }

    private fun plantBomb() {
        viewModelScope.launch {
            Log.i(LOG_TAG,"BOMB PLANTED")
            timer.setDuration(TIMER_TOTAL_SECONDS)
            timer.start()
            timer.flow.takeWhile { it >= 0 }.collect { currentSeconds ->
                _uiState.emit(
                    BombScreenUiState.Planted(
                        password = (_uiState.value as BombScreenUiState.Planted).password,
                        totalSeconds = TIMER_TOTAL_SECONDS,
                        currentMs = currentSeconds
                    )
                )
            }
            _uiState.emit(BombScreenUiState.Exploded)
        }

    }

    private fun doesPasswordMatch(password: String): Boolean {
        if (password == _uiState.value.password) {
            return true
        }
        return false
    }

    private fun generateRandomString(stringLength: Int = 10): String {
        return java.util.UUID.randomUUID().toString().substring(0, stringLength)
    }

    companion object {
        private const val TIMER_TOTAL_SECONDS = 60
    }
}