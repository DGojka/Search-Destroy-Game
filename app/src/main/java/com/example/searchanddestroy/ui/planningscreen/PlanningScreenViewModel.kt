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
class PlanningScreenViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(PlanningScreenUiState(mutableListOf()))
    val uiState: StateFlow<PlanningScreenUiState> = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
                _uiState.value =
                    _uiState.value.copy(saveSlots = repository.getAllSaveSlotsWithSettings())
        }
    }

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

    fun saveSettingsToSlot(slotId: Int, slotName: String) {
        with(_uiState.value) {
            val saveSlot = SaveSlot(
                slotName, GameSettings(
                    plantingPasswordLength = plantingPasswordLength,
                    defusingPasswordLength = defusingPasswordLength,
                    timeToExplode = timeToExplode,
                    wrongPasswordPenalty = wrongPasswordPenalty
                ), slotId
            )
            Log.e(saveSlot.id.toString(),saveSlot.id.toString())

            viewModelScope.launch(Dispatchers.IO) {
                if (saveSlots.getOrNull(slotId-1) == null) {
                    repository.addSaveSlotWithSettings(
                        saveSlot
                    )
                } else {
                    repository.updateSlot(saveSlot)
                }
             Log.e("asd",repository.getAllSaveSlotsWithSettings().toString())
                _uiState.value =
                    _uiState.value.copy(saveSlots = repository.getAllSaveSlotsWithSettings())
            }
        }
    }
}
