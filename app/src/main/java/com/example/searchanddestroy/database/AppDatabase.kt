package com.example.searchanddestroy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.searchanddestroy.database.dao.SettingsDao
import com.example.searchanddestroy.database.dao.SaveSlotDao
import com.example.searchanddestroy.database.entity.SaveSlotEntity
import com.example.searchanddestroy.database.entity.SettingsEntity

@Database(
    entities = [SaveSlotEntity::class,SettingsEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun settingsDao(): SettingsDao
    abstract fun saveSlotDao(): SaveSlotDao

    companion object{
        const val NAME = "search_and_destroy_db"
    }
}