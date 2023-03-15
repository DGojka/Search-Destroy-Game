package com.example.searchanddestroy.ui.bombscreen.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchanddestroy.R
import com.example.searchanddestroy.ui.bombscreen.Speaker
import com.example.searchanddestroy.ui.bombscreen.Timer
import com.example.searchanddestroy.ui.planningscreen.data.GameSettings
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import java.lang.StrictMath.ceil
import javax.inject.Inject

@HiltViewModel
class BombScreenViewModel @Inject constructor(
    private val speaker: Speaker,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow<BombScreenUiState>(BombScreenUiState.Init)
    val uiState: StateFlow<BombScreenUiState> = _uiState
    private var timer: Timer = Timer()
    private val gson = GsonBuilder().create()
    private val settingsJson: String? = savedStateHandle[SETTINGS]
    private lateinit var settings: GameSettings

    init {
        viewModelScope.launch {
            settings = gson.fromJson(
                settingsJson,
                GameSettings::class.java
            )
            _uiState.emit(BombScreenUiState.Loaded(generateRandomString(settings.plantingPasswordLength)))
        }
    }

    fun startPlanting(password: String) {
        if (doesPasswordMatch(password)) {
            plantBomb()
        } else {
            viewModelScope.launch {
                val statePlanted = _uiState.value as BombScreenUiState.Loaded
                Log.i(LOG_TAG, "WRONG PASSWORD PLANTING")
                _uiState.value =
                    statePlanted.copy(password = generateRandomString(settings.plantingPasswordLength))
            }
        }
    }

    fun startDefusing(password: String) {
        val statePlanted = _uiState.value as BombScreenUiState.Planted
        if (doesPasswordMatch(password = password)) {
            defuse(statePlanted)
        } else {
            Log.i(LOG_TAG, "WRONG PASSWORD DEFUSING")
            val timeWithPenalty =
                ceil((statePlanted.currentMs.toDouble()) / 1000 - settings.wrongPasswordPenalty).toInt()
            timer.setDuration(timeWithPenalty)
            if (timeWithPenalty <= 0) {
                viewModelScope.launch {
                    _uiState.emit(BombScreenUiState.Exploded)
                }
            } else {
                _uiState.value =
                    statePlanted.copy(password = generateRandomString(settings.defusingPasswordLength))
            }
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
                    generateRandomString(settings.defusingPasswordLength),
                    settings.timeToExplode,
                    settings.timeToExplode.toFloat()
                )
            )
            timer.setDuration(settings.timeToExplode)
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
        private val LOG_TAG = BombScreenViewModel::class.qualifiedName
        private const val SETTINGS = "settings"
    }
}