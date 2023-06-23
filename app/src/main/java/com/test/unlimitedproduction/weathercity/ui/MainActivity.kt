package com.test.unlimitedproduction.weathercity.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.unlimitedproduction.weathercity.App
import com.test.unlimitedproduction.weathercity.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
