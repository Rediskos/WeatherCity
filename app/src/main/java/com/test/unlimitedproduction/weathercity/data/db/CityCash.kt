package com.test.unlimitedproduction.weathercity.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel

@Entity
data class CityCash(
    @PrimaryKey(autoGenerate = false)
    val city: String,
    val temp: Double,
    val wind: Double,
    val humidity: Int,
    val isFavorite: Boolean = false,
    val weatherIcon: String
)

fun CityCash.mapToDomain() = WeatherModel(
    temp = temp,
    wind = wind,
    humidity = humidity,
    city = city,
    isFavorite = isFavorite,
    icon = weatherIcon
)
