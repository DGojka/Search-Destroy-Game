package com.example.searchanddestroy.di

import android.content.Context
import com.example.searchanddestroy.ui.bombscreen.Speaker
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
}