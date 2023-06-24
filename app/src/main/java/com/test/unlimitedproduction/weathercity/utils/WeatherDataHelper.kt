package com.test.unlimitedproduction.weathercity.utils

import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object WeatherDataHelper {
    const val DEFAULT_CITY = "London"

    private var currentWeatherData = MutableStateFlow<WeatherModel?>(null)
    var currentCity = DEFAULT_CITY

    suspend fun newWeatherData(data: WeatherModel) = currentWeatherData.emit(data)

    fun getCurrentWeatherData(): StateFlow<WeatherModel?> = currentWeatherData

}
