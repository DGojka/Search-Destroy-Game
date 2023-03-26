package com.example.searchanddestroy.ui.planningscreen

import com.example.searchanddestroy.database.DefaultSettings
import com.example.searchanddestroy.repository.SaveSlot

data class PlanningScreenUiState(
    var saveSlots: List<SaveSlot>,
    var plantingPasswordLength: Int = DefaultSettings.DEFAULT_PLANTING_PASSWORD_LENGTH,
    var defusingPasswordLength: Int = DefaultSettings.DEFAULT_DEFUSING_PASSWORD_LENGTH,
    var timeToExplode: Int = DefaultSettings.DEFAULT_TIME_TO_EXPLODE,
    var wrongPasswordPenalty: Int = DefaultSettings.DEFAULT_WRONG_PASSWORD_PENALTY_LENGTH
)

