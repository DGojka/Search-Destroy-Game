package com.example.searchanddestroy.ui.planningscreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlanningScreenViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(PlanningScreenUiState())
    val uiState: StateFlow<PlanningScreenUiState> = _uiState

    fun changePlantingPasswordLength(passwordLength: Int) {
        _uiState.value = _uiState.value.copy(plantingPasswordLength = passwordLength)
    }

    fun changeDefusingPasswordLength(passwordLength: Int) {
        _uiState.value = _uiState.value.copy(defusingPasswordLength = passwordLength)
    }

    fun changeTimeToExplode(timeToExplode: Int) {
        _uiState.value = _uiState.value.copy(timeToExplodeLength = timeToExplode)
    }

    fun changeWrongPasswordPenalty(penaltyTime: Int) {
        _uiState.value = _uiState.value.copy(wrongPasswordPenalty = penaltyTime)
    }
}