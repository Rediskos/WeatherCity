package com.test.unlimitedproduction.weathercity.data

import android.util.Log
import com.test.unlimitedproduction.weathercity.data.db.CityCashDataBase
import com.test.unlimitedproduction.weathercity.data.db.mapToDomain
import com.test.unlimitedproduction.weathercity.data.network.WeatherApi
import com.test.unlimitedproduction.weathercity.data.network.response.weather.WeatherInfo
import com.test.unlimitedproduction.weathercity.data.network.response.weather.mapToDataBase
import com.test.unlimitedproduction.weathercity.data.network.response.weather.mapToDomain
import com.test.unlimitedproduction.weathercity.domain.WeatherRepository
import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel
import com.test.unlimitedproduction.weathercity.utils.WeatherDataHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherRepositoryImpl(private val api: WeatherApi, private val db: CityCashDataBase) : WeatherRepository {
    override suspend fun getWeatherInformer(): StateFlow<WeatherModel?> {
        return WeatherDataHelper.getCurrentWeatherData()
    }

    private suspend fun checkWeatherInCash(currentCity: String) {
        try {
            db.dao.getCityCash(currentCity).let {
                WeatherDataHelper.newWeatherData(it.last().mapToDomain())
            }
        } catch (e: NoSuchElementException) {
            e.printStackTrace()
        }
    }

    override suspend fun weatherForCity(city: String) {
        checkWeatherInCash(city)
        val response = api.getWeather(city)
        if (response.isSuccessful) {
            response.body()?.let {
                WeatherDataHelper.newWeatherData(it.mapToDomain())
                cashData(it)
            }
        }
    }

    private suspend fun cashData(weatherInfo: WeatherInfo) {
        db.dao.upsertCash(weatherInfo.mapToDataBase())
    }
}
