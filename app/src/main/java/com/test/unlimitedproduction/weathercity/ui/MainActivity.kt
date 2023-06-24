package com.test.unlimitedproduction.weathercity.ui

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.test.unlimitedproduction.weathercity.App
import com.test.unlimitedproduction.weathercity.R
import com.test.unlimitedproduction.weathercity.utils.EventHelper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        App.instance.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pb = findViewById<ProgressBar>(R.id.pb_loading)
        lifecycleScope.launch {
            EventHelper.isLoading.collectLatest {
                pb.isVisible = it
            }
        }
    }
}
