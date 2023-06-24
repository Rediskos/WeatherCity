package com.test.unlimitedproduction.weathercity.data.network

import com.test.unlimitedproduction.weathercity.data.network.response.city.CityList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CityApi {
    @GET("city")
    suspend fun getCity(@Query("name") name: String, @Query("limit") limit: Int = 10): Response<CityList>
}
