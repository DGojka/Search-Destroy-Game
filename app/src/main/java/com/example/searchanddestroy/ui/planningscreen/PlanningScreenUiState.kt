package com.example.searchanddestroy.ui.planningscreen

data class PlanningScreenUiState(
    val plantingPasswordLength: Int = 10,
    val defusingPasswordLength: Int = 10,
    val timeToExplodeLength: Int = 180,
    val wrongPasswordPenalty: Int = 3
)