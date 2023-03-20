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
                )
            )
        }
    }

    override suspend fun getSaveSlotById(id: Long): SaveSlot {
        TODO("Not yet implemented")
    }

    override suspend fun addSaveSlotWithSettings(slot: SaveSlot) {
        val slotId = generateSlotId()
        val settingsId = generateSettingId()
        db.saveSlotDao().add(SaveSlotEntity(slotId, slot.name))
        with(slot.settings) {
            db.settingsDao().add(
                SettingsEntity(
                    id = settingsId,
                    plantingPasswordLength = plantingPasswordLength,
                    defusingPasswordLength = defusingPasswordLength,
                    timeToExplode = timeToExplode,
                    wrongPasswordPenalty = wrongPasswordPenalty,
                    saveSlotOwnerId = slotId
                )
            )
        }
    }

    override suspend fun updateSlot(slotId: Long, newSettings: SettingsEntity) {
        TODO("Not yet implemented")
    }

    private fun generateSlotId(): Long =
        db.saveSlotDao().getAllSlots().maxOfOrNull { it.slotId + 1 } ?: 0

    private fun generateSettingId(): Long =
        db.settingsDao().getAll().maxOfOrNull { it.id + 1 } ?: 0
}