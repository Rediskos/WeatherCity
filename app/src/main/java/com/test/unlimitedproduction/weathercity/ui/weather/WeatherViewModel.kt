package com.test.unlimitedproduction.weathercity.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.unlimitedproduction.weathercity.domain.CityRepository
import com.test.unlimitedproduction.weathercity.domain.WeatherRepository
import com.test.unlimitedproduction.weathercity.domain.model.WeatherModel
import com.test.unlimitedproduction.weathercity.utils.EventHelper
import com.test.unlimitedproduction.weathercity.utils.WeatherDataHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class WeatherViewModel @Inject constructor(
        private val repository: WeatherRepository,
        private val cityRepository: CityRepository
    ) : ViewModel() {

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

    fun setCityIsFavoriteState(name: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            cityRepository.setCityFavoriteState(name, isFavorite)
        }
    }

    private fun checkWeather() {
        viewModelScope.launch(Dispatchers.IO){
            repository.weatherForCity(WeatherDataHelper.currentCity)
        }
    }
}
