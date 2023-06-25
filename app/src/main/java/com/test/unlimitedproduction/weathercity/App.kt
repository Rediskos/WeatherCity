package com.test.unlimitedproduction.weathercity

import android.app.Application
import android.content.res.Resources
import com.test.unlimitedproduction.weathercity.di.AppComponent
import com.test.unlimitedproduction.weathercity.di.DaggerAppComponent

class App: Application() {
    companion object {
        lateinit var instance: App
            private set
        lateinit var res: Resources
            private set
    }

    val appComponent: AppComponent = DaggerAppComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()
        instance = this
        res = resources
    }
}
