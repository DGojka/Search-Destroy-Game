package com.example.searchanddestroy.ui.planningscreen.data

data class GameSettings(
    val plantingPasswordLength: Int = 10,
    val defusingPasswordLength: Int = 10,
    val timeToExplode: Int = 180,
    val wrongPasswordPenalty: Int = 30
)