package com.test.unlimitedproduction.weathercity.di

import com.test.unlimitedproduction.weathercity.data.CityRepositoryImpl
import com.test.unlimitedproduction.weathercity.data.WeatherRepositoryImpl
import com.test.unlimitedproduction.weathercity.data.db.CityCashDataBase
import com.test.unlimitedproduction.weathercity.data.network.CityApi
import com.test.unlimitedproduction.weathercity.data.network.WeatherApi
import com.test.unlimitedproduction.weathercity.domain.CityRepository
import com.test.unlimitedproduction.weathercity.domain.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun WeatherRepository(api: WeatherApi, dataBase: CityCashDataBase, cityApi: CityApi):WeatherRepository
    = WeatherRepositoryImpl(api, dataBase, cityApi)

    @Singleton
    @Provides
    fun CityRepository(api: CityApi, dataBase: CityCashDataBase, weatherApi: WeatherApi): CityRepository
        = CityRepositoryImpl(api, dataBase, weatherApi)
}
