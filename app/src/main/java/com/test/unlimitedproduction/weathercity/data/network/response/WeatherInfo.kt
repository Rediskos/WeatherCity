package com.test.unlimitedproduction.weathercity.data.network.response

import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel

data class WeatherInfo(
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

fun WeatherInfo.mapToDomain() = WeatherModel(temp = main.temp, wind = wind.speed, humidity = main.humidity)
