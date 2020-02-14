package com.reportedsocks.imageuploadapp

import android.app.Application
import com.reportedsocks.imageuploadapp.data.di.ContextModule
import com.reportedsocks.imageuploadapp.data.di.DataBaseModule
import com.reportedsocks.imageuploadapp.data.di.RetrofitModule
import com.reportedsocks.imageuploadapp.data.di.ViewModelModule
import com.reportedsocks.imageuploadapp.ui.main.MainFragment
import com.reportedsocks.imageuploadapp.ui.savedlinks.SavedLinksFragment
import dagger.Component
import javax.inject.Singleton

/**
 * This is application component for Dagger which will provide dependecies in the app
 * */

@Singleton @Component( modules = [
    ViewModelModule::class,
    ContextModule::class,
    RetrofitModule::class,
    DataBaseModule::class ] )
interface ApplicationComponent{

    fun inject(fragment: MainFragment)
    fun inject(fragment: SavedLinksFragment)

}

class MyApp: Application() {
    val appComponent = DaggerApplicationComponent.builder()
        .contextModule(ContextModule(this)).build()
}