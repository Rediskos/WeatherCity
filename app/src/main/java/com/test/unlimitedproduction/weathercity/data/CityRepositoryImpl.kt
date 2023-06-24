package com.test.unlimitedproduction.weathercity.data

import com.test.unlimitedproduction.weathercity.data.network.CityApi
import com.test.unlimitedproduction.weathercity.data.network.response.city.mapToDomain
import com.test.unlimitedproduction.weathercity.domain.CityRepository
import com.test.unlimitedproduction.weathercity.domain.model.CityModel

class CityRepositoryImpl(private val api: CityApi): CityRepository {

    override suspend fun searchCityByName(name: String): List<CityModel>? {
        val response = api.getCity(name)
        return if (response.isSuccessful) {
            response.body()?.mapToDomain()
        } else {
            null
        }
    }
}
