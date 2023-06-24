package com.test.unlimitedproduction.weathercity.ui.weather

import android.location.Location
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
    }

    fun setCityIsFavoriteState(name: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            cityRepository.setCityFavoriteState(name, isFavorite)
        }
    }

    fun checkWeather() {
        viewModelScope.launch(Dispatchers.IO){
            if (WeatherDataHelper.currentCity != null)
                repository.weatherForCity(WeatherDataHelper.currentCity ?: WeatherDataHelper.DEFAULT_CITY)
            else if (WeatherDataHelper.lastKnownLocation != null)
                repository.weatherForLocation(WeatherDataHelper.lastKnownLocation!!)
        }
    }
}
