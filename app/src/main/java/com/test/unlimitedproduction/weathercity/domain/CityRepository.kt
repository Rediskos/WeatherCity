package com.test.unlimitedproduction.weathercity.domain

import com.test.unlimitedproduction.weathercity.domain.model.CityModel

interface CityRepository {
    suspend fun searchCityByName(name: String): List<CityModel>?

    suspend fun isCityFavorite(name: String): Boolean
    suspend fun setCityFavoriteState(name: String, isFavorite: Boolean)
}
