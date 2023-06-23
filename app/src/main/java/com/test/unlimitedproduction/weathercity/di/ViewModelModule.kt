package com.test.unlimitedproduction.weathercity.di

import com.test.unlimitedproduction.weathercity.domain.WeatherRepository
import com.test.unlimitedproduction.weathercity.ui.weather.WeatherViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideWeatherViewModel(weatherRepository: WeatherRepository) = WeatherViewModel(weatherRepository)
}
