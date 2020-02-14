package com.reportedsocks.imageuploadapp.data.di

import com.reportedsocks.imageuploadapp.domain.repository.ImgurAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Provides Retrofit implementation
 */
@Module
class RetrofitModule {
    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ImgurAPI.server)
            .build()
    }
    @Singleton
    @Provides
    fun getImgurAPI(retrofit: Retrofit): ImgurAPI {
        return retrofit.create(ImgurAPI::class.java)
    }

}