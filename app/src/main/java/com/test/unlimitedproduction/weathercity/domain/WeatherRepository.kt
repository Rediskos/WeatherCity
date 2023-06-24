package com.test.unlimitedproduction.weathercity.domain

import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


interface WeatherRepository {
    suspend fun getWeather() : StateFlow<WeatherModel?>

    suspend fun newCity(city: String)
}
