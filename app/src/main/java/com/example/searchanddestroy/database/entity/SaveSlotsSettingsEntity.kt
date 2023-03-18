package com.example.searchanddestroy.database.entity

import androidx.room.Entity

@Entity(tableName = "saveslots_settings", primaryKeys = ["ssId", "sId"])
data class SaveSlotsSettingsEntity(val ssId: Long, val sId: Long)