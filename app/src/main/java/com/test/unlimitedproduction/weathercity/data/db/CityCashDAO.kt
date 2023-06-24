package com.test.unlimitedproduction.weathercity.data.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CityCashDAO {

    @Upsert
    suspend fun upsertCash(cityCash: CityCash)

    @Query("SELECT * FROM CityCash WHERE city = :name")
    fun getCityCash(name: String): List<CityCash>
}
