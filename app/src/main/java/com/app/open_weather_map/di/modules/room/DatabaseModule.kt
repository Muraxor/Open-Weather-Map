package com.app.open_weather_map.di.modules.room

import android.content.Context
import androidx.room.Room
import com.app.open_weather_map.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DaoModule::class])
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(appContext: Context): AppDatabase = Room
        .databaseBuilder(appContext, AppDatabase::class.java, databaseName)
        .build()

    companion object {

        private const val databaseName = "app_database"
    }
}
