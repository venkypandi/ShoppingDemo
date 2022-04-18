package com.venkatesh.shoppingdemo.di.module

import android.content.Context
import androidx.room.Room
import com.venkatesh.shoppingdemo.data.local.dao.CartDao
import com.venkatesh.shoppingdemo.data.local.db.CartDatabase
import com.venkatesh.shoppingdemo.data.remote.api.ShoppingApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): CartDatabase {
        return Room.databaseBuilder(
            appContext,
            CartDatabase::class.java,
            "cart_database"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideCartDao(cartDatabase: CartDatabase): CartDao {
        return cartDatabase.cartDao()
    }
}
