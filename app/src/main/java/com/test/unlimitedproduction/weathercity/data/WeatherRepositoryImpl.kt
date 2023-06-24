package com.test.unlimitedproduction.weathercity.data

import android.location.Location
import com.test.unlimitedproduction.weathercity.data.db.CityCashDataBase
import com.test.unlimitedproduction.weathercity.data.db.mapToDomain
import com.test.unlimitedproduction.weathercity.data.network.CityApi
import com.test.unlimitedproduction.weathercity.data.network.WeatherApi
import com.test.unlimitedproduction.weathercity.data.network.response.weather.WeatherInfo
import com.test.unlimitedproduction.weathercity.data.network.response.weather.mapToDataBase
import com.test.unlimitedproduction.weathercity.data.network.response.weather.mapToDomain
import com.test.unlimitedproduction.weathercity.domain.WeatherRepository
import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel
import com.test.unlimitedproduction.weathercity.utils.EventHelper
import com.test.unlimitedproduction.weathercity.utils.WeatherDataHelper
import kotlinx.coroutines.flow.StateFlow

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val db: CityCashDataBase,
    private val cityApi: CityApi
) : WeatherRepository {
    override suspend fun getWeatherInformer(): StateFlow<WeatherModel?> {
        return WeatherDataHelper.getCurrentWeatherData()
    }

    private suspend fun checkWeatherInCash(currentCity: String) {
        try {
            db.dao.getCityCash(currentCity).let {
                WeatherDataHelper.newWeatherData(it.last().mapToDomain())
            }
        } catch (e: NoSuchElementException) {
            e.printStackTrace()
        }
    }

    override suspend fun weatherForCity(city: String) {
        EventHelper.showLoader()
        checkWeatherInCash(city)
        val response = try {
            api.getWeather(city)
        } catch (e: Exception) {
            null
        }
        if (response?.isSuccessful == true) {
            response.body()?.let {
                val isFavorite = try {
                    db.dao.isCityFavorite(it.name)
                } catch (e: NoSuchElementException) {
                    false
                }
                WeatherDataHelper.currentCity = it.name
                WeatherDataHelper.newWeatherData(it.mapToDomain(isFavorite))
                cashData(it)
            }
        }
        EventHelper.hideLoader()
    }

    override suspend fun weatherForLocation(location: Location) {
        EventHelper.showLoader()
        val lat = location.latitude
        val lon = location.longitude
        val name = try {
            cityApi.getCity(
                min_lat = lat - CityApi.COORDS_SHIFT,
                max_lat = lat + CityApi.COORDS_SHIFT,
                min_lon = lon - CityApi.COORDS_SHIFT,
                max_lon = lon + CityApi.COORDS_SHIFT,
            ).body()?.last()?.name
        } catch (e: Exception) {
            null
        }

        name?.let {
            checkWeatherInCash(it)
            val response = try {
                api.getWeather(it)
            } catch (e: Exception) {
                null
            }
            if (response?.isSuccessful == true) {
                response.body()?.let { weatherInfo ->
                    val isFavorite = try {
                        db.dao.isCityFavorite(weatherInfo.name)
                    } catch (e: NoSuchElementException) {
                        false
                    }
                    WeatherDataHelper.newWeatherData(weatherInfo.mapToDomain(isFavorite))
                    cashData(weatherInfo)
                }
            }
        }


        EventHelper.hideLoader()
    }

    override suspend fun cashDataForCity(city: String) {
        val response = try {
            api.getWeather(city)
        } catch (e: Exception) {
            null
        }
        if (response?.isSuccessful == true) {
            response.body()?.let {
                cashData(it)
            }
        }
    }

    private suspend fun cashData(weatherInfo: WeatherInfo) {
        //todo find better way to work with db
        try {
            val cashIsFavorite = db.dao.isCityFavorite(weatherInfo.name)
            db.dao.upsertCash(weatherInfo.mapToDataBase().copy(isFavorite = cashIsFavorite))
        } catch (e: NoSuchElementException) {
            db.dao.upsertCash(weatherInfo.mapToDataBase())
        }
    }
}
