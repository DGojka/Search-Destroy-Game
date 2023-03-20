package com.example.searchanddestroy.database.dao

import androidx.room.*
import com.example.searchanddestroy.database.entity.SaveSlotEntity
import com.example.searchanddestroy.database.entity.SaveSlotWithSettingsEntity

@Dao
interface SaveSlotDao {
    @Query("SELECT * FROM save_slot")
    fun getAllSlots(): List<SaveSlotEntity>

    @Transaction
    @Query("SELECT * FROM save_slot")
    fun getAllSlotsWithSettings(): List<SaveSlotWithSettingsEntity>

    @Insert
    fun add(saveSlotEntity: SaveSlotEntity)

    @Delete
    fun delete(saveSlotEntity: SaveSlotEntity)

    @Update
    fun update(saveSlotEntity: SaveSlotEntity)
}
