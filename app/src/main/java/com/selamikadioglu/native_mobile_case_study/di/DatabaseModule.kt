package com.selamikadioglu.native_mobile_case_study.di

import android.content.Context
import androidx.room.Room
import com.selamikadioglu.native_mobile_case_study.local.dao.FavoriteProductDao
import com.selamikadioglu.native_mobile_case_study.local.dao.OrderDao
import com.selamikadioglu.native_mobile_case_study.local.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideProductsDatabase(@ApplicationContext context : Context) : ProductDatabase {
        return Room.databaseBuilder(context, ProductDatabase::class.java,"product_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFavoriteProductDao(database: ProductDatabase): FavoriteProductDao {
        return database.favoriteProductDao()
    }

    @Provides
    fun provideOrderDao(database: ProductDatabase): OrderDao {
        return database.orderDao()
    }
}