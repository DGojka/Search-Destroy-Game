package com.example.searchanddestroy.database.entity

import android.provider.Settings
import androidx.room.Embedded
import androidx.room.Relation

data class SlotWithSettingsEntityy(
    @Embedded
    val saveSlotEntity: SaveSlotEntity,
    @Relation(parentColumn = "slotId", entityColumn = "saveSlotOwnerId")
    val settings: Settings
)