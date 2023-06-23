package com.test.unlimitedproduction.weathercity.di

import com.test.unlimitedproduction.weathercity.App
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class, RepositoryModule::class])
@Singleton
interface AppComponent {
    fun inject(app: App)
}
