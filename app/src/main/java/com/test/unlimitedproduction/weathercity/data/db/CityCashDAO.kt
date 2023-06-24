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

    @Query("SELECT isFavorite FROM CityCash WHERE city = :name")
    fun isCityFavorite(name: String): Boolean

    @Query("SELECT * FROM CityCash WHERE isFavorite = 1")
    fun getAllFavorite(): List<CityCash>

    @Query("UPDATE CityCash SET isFavorite = :isFavorite WHERE city = :name")
    fun setCityFavoriteState(name: String, isFavorite: Boolean)
}
