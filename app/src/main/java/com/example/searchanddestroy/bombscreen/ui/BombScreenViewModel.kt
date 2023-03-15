package com.example.searchanddestroy.bombscreen.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchanddestroy.R
import com.example.searchanddestroy.bombscreen.Speaker
import com.example.searchanddestroy.bombscreen.Timer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BombScreenViewModel @Inject constructor(private val speaker: Speaker) : ViewModel() {
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
            plantBomb()
        } else {
            viewModelScope.launch {
                val statePlanted = _uiState.value as BombScreenUiState.Loaded
                Log.i(LOG_TAG, "WRONG PASSWORD PLANTING")
                _uiState.value = statePlanted.copy(password = generateRandomString())
            }
        }
    }

    fun startDefusing(password: String) {
        val statePlanted = _uiState.value as BombScreenUiState.Planted
        if (doesPasswordMatch(password = password)) {
            defuse(statePlanted)
        } else {
            Log.i(LOG_TAG, "WRONG PASSWORD DEFUSING")
            _uiState.value = statePlanted.copy(password = generateRandomString())
        }
    }

    private fun defuse(state: BombScreenUiState.Planted) {
        Log.i(LOG_TAG, "BOMB DEFUSED")
        viewModelScope.launch {
            speaker.say(stringId = R.string.bomb_defused)
        }
        timer.pause()
        _uiState.value = state.copy(defused = true)
    }

    private fun plantBomb() {
        viewModelScope.launch {
            speaker.say(stringId = R.string.bomb_planted)
            Log.i(LOG_TAG, "BOMB PLANTED")

            _uiState.emit(
                BombScreenUiState.Planted(
                    generateRandomString(),
                    TIMER_TOTAL_SECONDS,
                    TIMER_TOTAL_SECONDS.toFloat()
                )
            )
            timer.setDuration(TIMER_TOTAL_SECONDS)
            timer.start()
            timer.flow.takeWhile { it >= 0 }.collect { currentMs ->
                _uiState.value =
                    (_uiState.value as BombScreenUiState.Planted).copy(currentMs = currentMs)
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