package com.example.searchanddestroy.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.searchanddestroy.database.AppDatabase
import com.example.searchanddestroy.repository.Repository
import com.example.searchanddestroy.repository.RepositoryImpl
import com.example.searchanddestroy.sounds.Player
import com.example.searchanddestroy.sounds.Speaker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

class AppModule {

    @Provides
    @Singleton
    fun provideSpeaker(@ApplicationContext appContext: Context): Speaker {
        return Speaker(appContext)
    }

    @Provides
    @Singleton
    fun providePlayer(@ApplicationContext appContext: Context): Player {
        return Player(appContext)
    }

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        AppDatabase.NAME
    ).build()

    @Provides
    @Singleton
    fun provideRepository(db: AppDatabase): Repository{
        return RepositoryImpl(db)
    }
}

