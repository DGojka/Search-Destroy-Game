package com.example.searchanddestroy.ui.bombscreen.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchanddestroy.R
import com.example.searchanddestroy.sounds.Player
import com.example.searchanddestroy.sounds.SoundTrack
import com.example.searchanddestroy.sounds.Speaker
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
    private val player: Player,
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
            applyPenalty(statePlanted)
            _uiState.value =
                statePlanted.copy(password = generateRandomString(settings.defusingPasswordLength))
        }
    }

    fun updateCurrentPassword(password: String) {
        val statePlanted = _uiState.value as BombScreenUiState.Planted
        _uiState.value =
            statePlanted.copy(currentPasswordLength = password.length)
        if(password.length == settings.defusingPasswordLength){
            startDefusing(password)
        }
    }

    private fun defuse(state: BombScreenUiState.Planted) {
        Log.i(LOG_TAG, "BOMB DEFUSED")
        viewModelScope.launch {
            speaker.say(stringId = R.string.bomb_defused)
        }
        timer.pause()
        player.stopBombSound()
        _uiState.value = state.copy(defused = true)
    }

    private fun plantBomb() {
        viewModelScope.launch {
            initBomb()
            onBombPlanted()
        }
    }

    private suspend fun initBomb() {
        player.playSound(SoundTrack.BOMB_PLANTED)
        player.playBombSound()
        Log.i(LOG_TAG, "BOMB PLANTED")
        _uiState.emit(
            BombScreenUiState.Planted(
                password = generateRandomString(settings.defusingPasswordLength),
                totalSeconds = settings.timeToExplode,
                currentMs =  settings.timeToExplode.toFloat(),
                currentPasswordLength = 0,
            )
        )
    }

    private suspend fun onBombPlanted() {
        timer.setDuration(settings.timeToExplode)
        timer.start()
        timer.flow.takeWhile { it >= 0 }.collect { currentMs ->
            _uiState.value =
                (_uiState.value as BombScreenUiState.Planted).copy(currentMs = currentMs)
            if (currentMs / 1000 == intenseBombBeepingTime) {
                player.stopBombSound()
                player.playSound(SoundTrack.INTENSE_BOMB_SOUND)
            }
        }
        explode()
    }

    private suspend fun explode() {
        _uiState.emit(BombScreenUiState.Exploded)
        player.playSound(SoundTrack.EXPLOSION)
        player.stopBombSound()
    }

    private fun applyPenalty(state: BombScreenUiState.Planted) {
        val timeWithPenalty =
            ceil((state.currentMs).toDouble() / 1000 - settings.wrongPasswordPenalty).toInt()
        timer.setDuration(timeWithPenalty)
    }

    private fun doesPasswordMatch(password: String): Boolean {
        if (password == _uiState.value.password) {
            return true
        }
        return false
    }

    private fun generateRandomString(stringLength: Int = 10): String {
        if (singleStringMaxValue < stringLength) {
            return generateStringLongerThanSingleUUIDLength(stringLength).substring(0, stringLength)
        }
        return java.util.UUID.randomUUID().toString().substring(0, stringLength)
    }

    private fun generateStringLongerThanSingleUUIDLength(stringLength: Int): String {
        val numberOfStringsToGenerate =
            ceil(stringLength.toDouble() / singleStringMaxValue.toDouble()).toInt()
        var randomString = ""
        for (i in 1..numberOfStringsToGenerate) {
            randomString += java.util.UUID.randomUUID().toString()
        }
        return randomString
    }

    companion object {
        private const val singleStringMaxValue = 36
        private const val intenseBombBeepingTime = 13.5F
        private val LOG_TAG = BombScreenViewModel::class.qualifiedName
        private const val SETTINGS = "settings"
    }
}