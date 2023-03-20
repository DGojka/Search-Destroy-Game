package com.example.searchanddestroy.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SaveSlotWithSettingsEntity(
    @Embedded
    val saveSlotEntity: SaveSlotEntity,
    @Relation(parentColumn = "slotId", entityColumn = "saveSlotOwnerId")
    val settings: SettingsEntity
)