package com.test.unlimitedproduction.weathercity.di

import com.test.unlimitedproduction.weathercity.data.WeatherRepositoryImpl
import com.test.unlimitedproduction.weathercity.data.network.WeatherApi
import com.test.unlimitedproduction.weathercity.domain.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun WeatherRepository(api: WeatherApi):WeatherRepository = WeatherRepositoryImpl(api)
}
