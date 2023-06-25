package com.test.unlimitedproduction.weathercity.di

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
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
    fun WeatherRepository(
        api: WeatherApi,
        dataBase: CityCashDataBase,
        cityApi: CityApi,
        imageCasher: RequestManager
    ):WeatherRepository
    = WeatherRepositoryImpl(api, dataBase, cityApi, imageCasher)

    @Singleton
    @Provides
    fun CityRepository(
        api: CityApi,
        dataBase: CityCashDataBase,
        weatherApi: WeatherApi,
        imageCasher: RequestManager
    ): CityRepository
        = CityRepositoryImpl(api, dataBase, weatherApi, imageCasher)
}
