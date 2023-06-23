package com.test.unlimitedproduction.weathercity.ui.weather

import androidx.lifecycle.ViewModel
import com.test.unlimitedproduction.weathercity.domain.WeatherRepository
import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class WeatherViewModel(repository: WeatherRepository) : ViewModel() {

    private val _currentWeatherData: MutableStateFlow<WeatherModel?> = MutableStateFlow(null)
    val currentWeatherData: StateFlow<WeatherModel?>
        get() = _currentWeatherData

    init {

    }
}
