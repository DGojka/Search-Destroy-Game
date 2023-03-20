package com.example.searchanddestroy.database.dao

import androidx.room.*
import com.example.searchanddestroy.database.entity.SettingsEntity

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings")
    fun getAll(): List<SettingsEntity>

    @Insert
    fun add(settingsEntity: SettingsEntity)

    @Delete
    fun delete(settingsEntity: SettingsEntity)

    @Update
    fun update(settingsEntity: SettingsEntity)
}