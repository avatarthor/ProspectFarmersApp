package com.android.goodnatureagro.di

import android.content.Context
import androidx.room.Room
import com.android.goodnatureagro.data.local.AppDatabase
import com.android.goodnatureagro.data.local.dao.FarmerDao
import com.android.goodnatureagro.data.remote.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideFarmerDao(appDatabase: AppDatabase): FarmerDao {
        return appDatabase.farmerDao()
    }

    @Provides
    @Singleton
    fun provideNetworkUtils(
        @ApplicationContext context: Context
    ): NetworkUtils {
        return NetworkUtils(context)
    }
}