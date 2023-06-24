package com.test.unlimitedproduction.weathercity

import android.app.Application
import com.test.unlimitedproduction.weathercity.di.AppComponent
import com.test.unlimitedproduction.weathercity.di.DaggerAppComponent

class App: Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    val appComponent: AppComponent = DaggerAppComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
