package com.reportedsocks.imageuploadapp.data.di

import android.app.Application
import android.content.Context
import dagger.Module

import dagger.Provides
import javax.inject.Singleton

/**
 * Provides application context
 */

@Module
class ContextModule(private val application: Application) {
    @Singleton
    @Provides
    fun getContext(): Context {
        return application.applicationContext
    }

}