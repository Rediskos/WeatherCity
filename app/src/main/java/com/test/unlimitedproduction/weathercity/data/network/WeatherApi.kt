package com.test.unlimitedproduction.weathercity.data.network

import com.test.unlimitedproduction.weathercity.data.network.response.WeatherInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(@Query("q") city: String, @Query("appid") appId: String) : Response<WeatherInfo>
}
