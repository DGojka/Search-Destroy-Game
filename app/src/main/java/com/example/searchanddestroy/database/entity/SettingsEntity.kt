package com.example.searchanddestroy.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.searchanddestroy.database.DefaultSettings

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(defaultValue = DefaultSettings.DEFAULT_PLANTING_PASSWORD_LENGTH.toString())
    val plantingPasswordLength: Int,
    @ColumnInfo(defaultValue = DefaultSettings.DEFAULT_DEFUSING_PASSWORD_LENGTH.toString())
    val defusingPasswordLength: Int,
    @ColumnInfo(defaultValue = DefaultSettings.DEFAULT_TIME_TO_EXPLODE.toString())
    val timeToExplode: Int,
    @ColumnInfo(defaultValue = DefaultSettings.DEFAULT_TIME_TO_EXPLODE.toString())
    val wrongPasswordPenalty: Int,
    val saveSlotOwnerId: Long
)
