package com.test.unlimitedproduction.weathercity.data

import com.test.unlimitedproduction.weathercity.BuildConfig
import com.test.unlimitedproduction.weathercity.data.network.WeatherApi
import com.test.unlimitedproduction.weathercity.data.network.response.mapToDomain
import com.test.unlimitedproduction.weathercity.domain.WeatherRepository
import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepositoryImpl(private val api: WeatherApi): WeatherRepository {
    override suspend fun getWeather(): Flow<WeatherModel> {
        return flow {
            emit(api.getWeather("London", BuildConfig.API_KEY).mapToDomain())
        }
    }
}
