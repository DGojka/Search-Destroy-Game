package com.example.searchanddestroy.ui.planningscreen

import androidx.lifecycle.ViewModel
import com.example.searchanddestroy.ui.planningscreen.data.GameSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlanningScreenViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(GameSettings())
    val uiState: StateFlow<GameSettings> = _uiState

    fun changePlantingPasswordLength(passwordLength: Int) {
        _uiState.value = _uiState.value.copy(plantingPasswordLength = passwordLength)
    }

    fun changeDefusingPasswordLength(passwordLength: Int) {
        _uiState.value = _uiState.value.copy(defusingPasswordLength = passwordLength)
    }

    fun changeTimeToExplode(timeToExplode: Int) {
        _uiState.value = _uiState.value.copy(timeToExplode = timeToExplode)
    }

    fun changeWrongPasswordPenalty(penaltyTime: Int) {
        _uiState.value = _uiState.value.copy(wrongPasswordPenalty = penaltyTime)
    }

}