package com.test.unlimitedproduction.weathercity.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.unlimitedproduction.weathercity.domain.WeatherRepository
import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel
import com.test.unlimitedproduction.weathercity.utils.WeatherDataHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class WeatherViewModel @Inject constructor(val repository: WeatherRepository) : ViewModel() {

    private val _currentWeatherData: MutableStateFlow<WeatherModel?> = MutableStateFlow(null)
    val currentWeatherData: StateFlow<WeatherModel?>
        get() = _currentWeatherData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getWeatherInformer().collect {
                _currentWeatherData.emit(it)
            }
        }
        checkWeather()
    }

    private fun checkWeather() {
        viewModelScope.launch(Dispatchers.IO){
            repository.weatherForCity(WeatherDataHelper.currentCity)
        }
    }
}
