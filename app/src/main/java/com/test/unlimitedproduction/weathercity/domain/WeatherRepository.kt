package com.test.unlimitedproduction.weathercity.domain

import android.location.Location
import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


interface WeatherRepository {
    suspend fun getWeatherInformer() : StateFlow<WeatherModel?>

    suspend fun weatherForCity(city: String)
    suspend fun weatherForLocation(location: Location)

    suspend fun cashDataForCity(city: String)
}
