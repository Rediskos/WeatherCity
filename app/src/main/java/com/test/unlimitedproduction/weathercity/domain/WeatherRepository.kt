package com.test.unlimitedproduction.weathercity.domain

import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {
    suspend fun getWeather() : Flow<WeatherModel>
}
