package com.test.unlimitedproduction.weathercity.data.network.response.weather

import com.google.gson.annotations.SerializedName
import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel

data class WeatherInfo(
    @SerializedName("main") val main: Main,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("name") val name: String
)

fun WeatherInfo.mapToDomain() = WeatherModel(temp = main.temp, wind = wind.speed, humidity = main.humidity, city = name)
