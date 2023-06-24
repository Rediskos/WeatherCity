package com.test.unlimitedproduction.weathercity.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.unlimitedproduction.weathercity.domain.CityRepository
import com.test.unlimitedproduction.weathercity.domain.WeatherRepository
import com.test.unlimitedproduction.weathercity.domain.model.CityModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val cityRepository: CityRepository,
    private val weatherRepository: WeatherRepository
    ): ViewModel() {
    private val _currentListOfCityData: MutableStateFlow<List<CityModel>?> = MutableStateFlow(null)
    val currentListOfCityData: StateFlow<List<CityModel>?>
        get() = _currentListOfCityData
    init {
        getAllFavorite()
    }

    fun getAllFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            cityRepository.getAllFavorite().let { _currentListOfCityData.emit(it) }
        }
    }

    fun setCityFavorite(name: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            cityRepository.setCityFavoriteState(name, isFavorite)
            getAllFavorite()
        }
    }
    fun newCity(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.weatherForCity(city)
        }
    }
}
