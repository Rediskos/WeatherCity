package com.test.unlimitedproduction.weathercity

import android.app.Application
import com.test.unlimitedproduction.weathercity.di.AppComponent

class App: Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    lateinit var appComponent: AppComponent
        private set


    override fun onCreate() {
        super.onCreate()
        instance = this
        initComponent()
    }

    private fun initComponent() {
//        appComponent = DaggerAppComponent.builder()
//            .build()
//        appComponent.inject(this)
    }
}
