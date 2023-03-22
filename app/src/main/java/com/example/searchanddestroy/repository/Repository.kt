package com.example.searchanddestroy.repository

interface Repository {
    suspend fun getAllSaveSlotsWithSettings() : List<SaveSlot>

    suspend fun getSaveSlotById(id : Long): SaveSlot

    suspend fun addSaveSlotWithSettings(slot : SaveSlot)

    suspend fun updateSlot(slot: SaveSlot)
}