package com.reportedsocks.imageuploadapp.data.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.reportedsocks.imageuploadapp.domain.model.Link
import com.reportedsocks.imageuploadapp.domain.repository.LinksDao

@Database(entities = arrayOf(Link::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun linksDao(): LinksDao
}