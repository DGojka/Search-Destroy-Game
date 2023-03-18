package com.example.searchanddestroy.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.searchanddestroy.database.entity.SaveSlotEntity

@Dao
interface SettingsDao {
    @Query("SELECT * FROM save_slot")
    fun getAll(): List<SaveSlotEntity>

    @Insert
    fun add(saveSlotEntity: SaveSlotEntity)

    @Delete
    fun delete(saveSlotEntity: SaveSlotEntity)

    @Update
    fun update(saveSlotEntity: SaveSlotEntity)
}
