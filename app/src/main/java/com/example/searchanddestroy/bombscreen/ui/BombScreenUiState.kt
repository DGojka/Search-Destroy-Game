package com.example.searchanddestroy.bombscreen.ui

sealed class BombScreenUiState(open val password: String) {
    object Init : BombScreenUiState("")
    object Error : BombScreenUiState("")
    object Exploded : BombScreenUiState("")
    data class Loaded(override val password: String) : BombScreenUiState(password)
    data class Planted(
        override val password: String,
        val totalSeconds: Int,
        val currentMs: Float,
        val defused: Boolean = false
    ) : BombScreenUiState(password)
}