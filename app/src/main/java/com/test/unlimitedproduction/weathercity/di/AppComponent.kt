package com.test.unlimitedproduction.weathercity.di

import android.content.Context
import com.test.unlimitedproduction.weathercity.App
import com.test.unlimitedproduction.weathercity.ui.MainActivity
import com.test.unlimitedproduction.weathercity.ui.search.CityFragment
import com.test.unlimitedproduction.weathercity.ui.weather.WeatherFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, RepositoryModule::class, DatabaseModule::class])
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
    fun inject(activity: MainActivity)
    fun inject(fragment: WeatherFragment)
    fun inject(fragment: CityFragment)
}
