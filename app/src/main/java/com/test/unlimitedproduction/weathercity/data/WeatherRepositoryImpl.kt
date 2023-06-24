package com.test.unlimitedproduction.weathercity.data

import com.test.unlimitedproduction.weathercity.data.network.WeatherApi
import com.test.unlimitedproduction.weathercity.data.network.response.weather.mapToDomain
import com.test.unlimitedproduction.weathercity.domain.WeatherRepository
import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel
import com.test.unlimitedproduction.weathercity.utils.WeatherDataHelper
import kotlinx.coroutines.flow.StateFlow

class WeatherRepositoryImpl(private val api: WeatherApi): WeatherRepository {
    override suspend fun getWeather(): StateFlow<WeatherModel?> {
        val response = api.getWeather(WeatherDataHelper.currentCity)
        if (response.isSuccessful) {
            response.body()?.let {
                WeatherDataHelper.newWeatherData(it.mapToDomain())
            }
        }
        return WeatherDataHelper.getCurrentWeatherData()
    }

    override suspend fun newCity(city: String) {
        val response = api.getWeather(city)
        if (response.isSuccessful) {
            response.body()?.let {
                WeatherDataHelper.newWeatherData(it.mapToDomain())
            }
        }
    }
}
