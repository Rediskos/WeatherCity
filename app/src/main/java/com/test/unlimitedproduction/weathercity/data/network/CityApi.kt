package com.test.unlimitedproduction.weathercity.data.network

import com.test.unlimitedproduction.weathercity.data.network.response.city.CityList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApi {
    @GET("city")
    suspend fun getCity(@Query("name") name: String, @Query("limit") limit: Int = 10): Response<CityList>

    @GET("city")
    suspend fun getCity(
        @Query("min_lat") min_lat: Double,
        @Query("max_lat") max_lat: Double,
        @Query("min_lon") min_lon: Double,
        @Query("max_lon") max_lon: Double,
        @Query("limit") limit: Int = 10
    ): Response<CityList>

    companion object {
        const val COORDS_SHIFT = 0.01
    }
}
