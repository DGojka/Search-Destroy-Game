package com.example.searchanddestroy.ui.planningscreen.data

import com.example.searchanddestroy.ui.planningscreen.data.DefaultSettings.Companion.DEFAULT_DEFUSING_PASSWORD_LENGTH
import com.example.searchanddestroy.ui.planningscreen.data.DefaultSettings.Companion.DEFAULT_PLANTING_PASSWORD_LENGTH
import com.example.searchanddestroy.ui.planningscreen.data.DefaultSettings.Companion.DEFAULT_TIME_TO_EXPLODE
import com.example.searchanddestroy.ui.planningscreen.data.DefaultSettings.Companion.DEFAULT_WRONG_PASSWORD_PENALTY_LENGTH

data class GameSettings(
    val plantingPasswordLength: Int = DEFAULT_PLANTING_PASSWORD_LENGTH,
    val defusingPasswordLength: Int = DEFAULT_DEFUSING_PASSWORD_LENGTH,
    val timeToExplode: Int = DEFAULT_TIME_TO_EXPLODE,
    val wrongPasswordPenalty: Int = DEFAULT_WRONG_PASSWORD_PENALTY_LENGTH
)