package com.example.searchanddestroy.repository

import com.example.searchanddestroy.ui.planningscreen.data.GameSettings

interface Repository {
    suspend fun getAllSaveSlotsWithSettings(): List<SaveSlot>

    suspend fun getSaveSlotById(id: Long): SaveSlot

    suspend fun addSaveSlotWithSettings(slot: SaveSlot)

    suspend fun updateSlot(slot: SaveSlot)

    suspend fun saveLastUsedSettings(settings: GameSettings)
}