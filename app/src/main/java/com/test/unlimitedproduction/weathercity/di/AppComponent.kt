package com.test.unlimitedproduction.weathercity.di

import com.test.unlimitedproduction.weathercity.App
import com.test.unlimitedproduction.weathercity.ui.MainActivity
import com.test.unlimitedproduction.weathercity.ui.weather.WeatherFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, RepositoryModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: WeatherFragment)
}
