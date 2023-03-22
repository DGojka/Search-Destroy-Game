package com.example.searchanddestroy.repository

import com.example.searchanddestroy.database.AppDatabase
import com.example.searchanddestroy.database.entity.SaveSlotEntity
import com.example.searchanddestroy.database.entity.SaveSlotWithSettingsEntity
import com.example.searchanddestroy.database.entity.SettingsEntity
import com.example.searchanddestroy.ui.planningscreen.data.GameSettings

class RepositoryImpl(private val db: AppDatabase) : Repository {
    override suspend fun getAllSaveSlotsWithSettings(): List<SaveSlot> {
        return db.saveSlotDao().getAllSlotsWithSettings().map {
            mapSlotsWithSessionsEntity(it)
        }
    }

    private fun mapSlotsWithSessionsEntity(saveSlotsWithSettingsEntity: SaveSlotWithSettingsEntity): SaveSlot {
        with(saveSlotsWithSettingsEntity.settings) {
            return SaveSlot(
                name = saveSlotsWithSettingsEntity.saveSlotEntity.slotName,
                settings = GameSettings(
                    plantingPasswordLength,
                    defusingPasswordLength,
                    timeToExplode,
                    wrongPasswordPenalty
                ),
                id = saveSlotOwnerId.toInt()
            )
        }
    }

    override suspend fun getSaveSlotById(id: Long): SaveSlot {
        TODO("Not yet implemented")
    }

    override suspend fun addSaveSlotWithSettings(slot: SaveSlot) {
        db.saveSlotDao().add(SaveSlotEntity(slot.id.toLong(), slot.name))
        with(slot.settings) {
            db.settingsDao().add(
                SettingsEntity(
                    id = slot.id.toLong(),
                    plantingPasswordLength = plantingPasswordLength,
                    defusingPasswordLength = defusingPasswordLength,
                    timeToExplode = timeToExplode,
                    wrongPasswordPenalty = wrongPasswordPenalty,
                    saveSlotOwnerId = slot.id.toLong()
                )
            )
        }
    }

    override suspend fun updateSlot(slot: SaveSlot) {
        db.saveSlotDao().update(SaveSlotEntity(slot.id.toLong(), slot.name))
        with(slot.settings) {
            db.settingsDao().update(
                SettingsEntity(
                    id = slot.id.toLong(),
                    plantingPasswordLength = plantingPasswordLength,
                    defusingPasswordLength = defusingPasswordLength,
                    timeToExplode = timeToExplode,
                    wrongPasswordPenalty = wrongPasswordPenalty,
                    saveSlotOwnerId = slot.id.toLong()
                )
            )
        }
    }
}