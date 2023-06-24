package com.test.unlimitedproduction.weathercity.domain

import com.test.unlimitedproduction.weathercity.domain.model.CityModel

interface CityRepository {
    suspend fun searchCityByName(name: String): List<CityModel>?
}
