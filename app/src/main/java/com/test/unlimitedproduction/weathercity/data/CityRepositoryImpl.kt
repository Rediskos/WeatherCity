package com.test.unlimitedproduction.weathercity.data

import com.test.unlimitedproduction.weathercity.data.db.CityCashDataBase
import com.test.unlimitedproduction.weathercity.data.network.CityApi
import com.test.unlimitedproduction.weathercity.data.network.response.city.mapToDomain
import com.test.unlimitedproduction.weathercity.domain.CityRepository
import com.test.unlimitedproduction.weathercity.domain.model.CityModel

class CityRepositoryImpl(private val api: CityApi, private val db: CityCashDataBase): CityRepository {

    override suspend fun searchCityByName(name: String): List<CityModel>? {
        val response = api.getCity(name)
        return if (response.isSuccessful) {
            response.body()?.mapToDomain()
        } else {
            null
        }
    }

    override suspend fun isCityFavorite(name: String): Boolean {
        return db.dao.isCityFavorite(name)
    }

    override suspend fun setCityFavoriteState(name: String, isFavorite: Boolean) {
        db.dao.setCityFavoriteState(name, isFavorite)
    }


}
