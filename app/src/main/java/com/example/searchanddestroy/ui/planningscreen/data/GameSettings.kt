package com.example.searchanddestroy.ui.planningscreen.data

import com.example.searchanddestroy.database.DefaultSettings.Companion.DEFAULT_DEFUSING_PASSWORD_LENGTH
import com.example.searchanddestroy.database.DefaultSettings.Companion.DEFAULT_PLANTING_PASSWORD_LENGTH
import com.example.searchanddestroy.database.DefaultSettings.Companion.DEFAULT_TIME_TO_EXPLODE
import com.example.searchanddestroy.database.DefaultSettings.Companion.DEFAULT_WRONG_PASSWORD_PENALTY_LENGTH

data class GameSettings(
    var plantingPasswordLength: Int = DEFAULT_PLANTING_PASSWORD_LENGTH,
    var defusingPasswordLength: Int = DEFAULT_DEFUSING_PASSWORD_LENGTH,
    var timeToExplode: Int = DEFAULT_TIME_TO_EXPLODE,
    var wrongPasswordPenalty: Int = DEFAULT_WRONG_PASSWORD_PENALTY_LENGTH
)