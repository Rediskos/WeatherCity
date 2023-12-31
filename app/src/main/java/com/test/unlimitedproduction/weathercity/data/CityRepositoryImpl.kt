package com.test.unlimitedproduction.weathercity.data

import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.test.unlimitedproduction.weathercity.App
import com.test.unlimitedproduction.weathercity.R
import com.test.unlimitedproduction.weathercity.data.db.CityCashDataBase
import com.test.unlimitedproduction.weathercity.data.db.mapToDomain
import com.test.unlimitedproduction.weathercity.data.network.CityApi
import com.test.unlimitedproduction.weathercity.data.network.WeatherApi
import com.test.unlimitedproduction.weathercity.data.network.response.weather.WeatherInfo
import com.test.unlimitedproduction.weathercity.data.network.response.weather.mapToDataBase
import com.test.unlimitedproduction.weathercity.data.network.response.weather.mapToDomain
import com.test.unlimitedproduction.weathercity.domain.CityRepository
import com.test.unlimitedproduction.weathercity.domain.model.CityModel
import com.test.unlimitedproduction.weathercity.utils.EventHelper
import com.test.unlimitedproduction.weathercity.utils.WeatherDataHelper

class CityRepositoryImpl(
    private val api: CityApi,
    private val db: CityCashDataBase,
    private val weatherApi: WeatherApi,
    private val imageCasher: RequestManager
) : CityRepository {

    override suspend fun searchCityByName(name: String): List<CityModel>? {
        EventHelper.showLoader()
        val response = try {
            api.getCity(name)
        } catch (e: Exception) {
            null
        }
        EventHelper.hideLoader()
        return if (response?.isSuccessful == true) {
            response.body()?.map { CityModel(it.name, isCityFavorite(it.name)) }
        } else {
            null
        }
    }

    override suspend fun isCityFavorite(name: String): Boolean {
        return try {
            db.dao.isCityFavorite(name)
        } catch (e: NoSuchElementException) {
            false
        }
    }

    override suspend fun setCityFavoriteState(name: String, isFavorite: Boolean) {
        if (db.dao.getCityCash(name).isNotEmpty()) {
            db.dao.setCityFavoriteState(name, isFavorite)
        } else if (isFavorite) {
            EventHelper.showLoader()
            val response = try {
                weatherApi.getWeather(name)
            } catch (e: Exception) {
                null
            }
            if (response?.isSuccessful == true) {
                response.body()?.let {
                    cashData(it, isFavorite)
                    WeatherDataHelper.newWeatherData(it.mapToDomain(isFavorite))
                }
            }
            EventHelper.hideLoader()
        }
    }

    override suspend fun getAllFavorite(): List<CityModel> = db.dao.getAllFavorite().map { CityModel(it.city, it.isFavorite) }

    private suspend fun cashData(weatherInfo: WeatherInfo, isFavorite: Boolean) {
        db.dao.upsertCash(weatherInfo.mapToDataBase().copy(isFavorite = isFavorite))
        imageCasher
            .load(App.res.getString(R.string.weather_icon_blueprint_url, weatherInfo.weather.last().icon))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .preload()
    }
}
