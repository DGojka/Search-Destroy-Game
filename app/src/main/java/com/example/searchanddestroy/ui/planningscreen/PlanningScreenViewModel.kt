package com.example.searchanddestroy.ui.planningscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchanddestroy.database.DefaultSettings
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
            val saveSlots = repository.getAllSaveSlotsWithSettings()
            val lastUsedSettings = saveSlots.getOrNull(DefaultSettings.DB_DEFAULT_SETTINGS_ID)
            if (lastUsedSettings != null) {
                with(lastUsedSettings.settings) {
                    _uiState.value = _uiState.value.copy(
                        plantingPasswordLength = plantingPasswordLength,
                        defusingPasswordLength = defusingPasswordLength,
                        timeToExplode = timeToExplode,
                        wrongPasswordPenalty = wrongPasswordPenalty
                    )
                }
            }
            _uiState.value = _uiState.value.copy(saveSlots = saveSlots)
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
                ), slotId + 1
            )

            viewModelScope.launch(Dispatchers.IO) {
                if (saveSlots.getOrNull(slotId) == null) {
                    repository.addSaveSlotWithSettings(
                        saveSlot
                    )
                } else {
                    repository.updateSlot(saveSlot)
                }
                _uiState.value =
                    _uiState.value.copy(saveSlots = repository.getAllSaveSlotsWithSettings())
            }
        }
    }

    fun importSettingsFromSlot(slotId: Int) {
        with(_uiState.value.saveSlots[slotId].settings) {
            _uiState.value = _uiState.value.copy(
                plantingPasswordLength = plantingPasswordLength,
                defusingPasswordLength = defusingPasswordLength,
                timeToExplode = timeToExplode,
                wrongPasswordPenalty = wrongPasswordPenalty
            )
        }
    }

    fun saveLastUsedSettingsToDb() {
        viewModelScope.launch(Dispatchers.IO) {
            with(_uiState.value) {
                repository.saveLastUsedSettings(
                    GameSettings(
                        plantingPasswordLength,
                        defusingPasswordLength,
                        timeToExplode,
                        wrongPasswordPenalty
                    )
                )

            }
        }
    }
}
