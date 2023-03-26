package com.example.searchanddestroy.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "save_slot")
data class SaveSlotEntity(
    @PrimaryKey val slotId: Long,
    val slotName: String
)
