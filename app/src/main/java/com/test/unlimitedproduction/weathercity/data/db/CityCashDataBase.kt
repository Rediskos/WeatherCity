package com.test.unlimitedproduction.weathercity.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CityCash::class],
    version = 1
)
abstract class CityCashDataBase: RoomDatabase() {

    abstract val dao: CityCashDAO
}
