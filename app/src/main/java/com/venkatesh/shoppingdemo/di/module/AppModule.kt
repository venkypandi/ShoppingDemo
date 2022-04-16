package com.venkatesh.shoppingdemo.di.module

import com.venkatesh.shoppingdemo.data.remote.api.ShoppingApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://my-json-server.typicode.com")
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ShoppingApiService {
        return retrofit.create(ShoppingApiService::class.java)
    }
}
