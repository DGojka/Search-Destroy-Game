package com.example.searchanddestroy.ui.planningscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchanddestroy.repository.Repository
import com.example.searchanddestroy.repository.SaveSlot
import com.example.searchanddestroy.ui.planningscreen.data.GameSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanningScreenViewModel @Inject constructor(private val repository : Repository) : ViewModel() {
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

    fun saveSettingsToSlot() {
        viewModelScope.launch(Dispatchers.IO){
            repository.addSaveSlotWithSettings(SaveSlot("1",_uiState.value))
            Log.e("asd",repository.getAllSaveSlotsWithSettings().toString())
        }
    }

}