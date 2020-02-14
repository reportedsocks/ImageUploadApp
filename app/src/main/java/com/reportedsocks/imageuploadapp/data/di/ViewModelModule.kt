package com.reportedsocks.imageuploadapp.data.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.reportedsocks.imageuploadapp.ui.main.MainViewModel
import com.reportedsocks.imageuploadapp.ui.savedlinks.SavedLinksViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Module which will provide Factory for ViewModel
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun mainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SavedLinksViewModel::class)
    internal abstract fun savedLinksViewModel(viewModel: SavedLinksViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory): ViewModelProvider.Factory

}