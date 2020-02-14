package com.reportedsocks.imageuploadapp.data.di

import android.content.Context
import androidx.room.Room
import com.reportedsocks.imageuploadapp.data.repository.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "links_database").build()
    }

}