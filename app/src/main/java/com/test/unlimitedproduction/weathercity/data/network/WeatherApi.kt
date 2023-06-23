package com.test.unlimitedproduction.weathercity.data.network

import com.test.unlimitedproduction.weathercity.data.network.response.WeatherInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("")
    suspend fun getWeather(@Query("q") city: String, @Query("appid") appId: String) : WeatherInfo
}
