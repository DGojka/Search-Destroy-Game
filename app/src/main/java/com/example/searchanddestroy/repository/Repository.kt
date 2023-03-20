package com.example.searchanddestroy.repository

import com.example.searchanddestroy.database.entity.SettingsEntity

interface Repository {
    suspend fun getAllSaveSlotsWithSettings() : List<SaveSlot>

    suspend fun getSaveSlotById(id : Long): SaveSlot

    suspend fun addSaveSlotWithSettings(slot : SaveSlot)

    suspend fun updateSlot(slotId: Long, newSettings: SettingsEntity)
}