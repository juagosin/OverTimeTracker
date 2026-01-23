package com.calleserpis.overtime.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.calleserpis.overtime.data.local.OvertimeDatabase
import com.calleserpis.overtime.data.preferences.OvertimePreferencesManager
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
    fun provideOvertimeDatabase(app: Application): OvertimeDatabase {
        return Room.databaseBuilder(
            app,
            OvertimeDatabase::class.java,
            "overtime_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideOvertimePreferencesManager(
        @ApplicationContext context: Context
    ): OvertimePreferencesManager {
        return OvertimePreferencesManager(context)
    }
}