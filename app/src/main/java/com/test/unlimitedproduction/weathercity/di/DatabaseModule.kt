package com.test.unlimitedproduction.weathercity.di

import android.content.Context
import androidx.room.Room
import com.test.unlimitedproduction.weathercity.data.db.CityCashDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context,
        CityCashDataBase::class.java,
        "cash.db"
    ).build()
}
