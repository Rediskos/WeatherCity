package com.test.unlimitedproduction.weathercity.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.unlimitedproduction.weathercity.domain.CityRepository
import com.test.unlimitedproduction.weathercity.domain.WeatherRepository
import com.test.unlimitedproduction.weathercity.domain.model.CityModel
import com.test.unlimitedproduction.weathercity.utils.EventHelper
import com.test.unlimitedproduction.weathercity.utils.WeatherDataHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitySearchViewModel @Inject constructor(
    private val cityRepository: CityRepository, private val weatherRepository: WeatherRepository): ViewModel(){
    fun onInputChanged(name: String) {
        findCitiesByName(name)
    }

    private val _currentListOfCityData: MutableStateFlow<List<CityModel>?> = MutableStateFlow(null)
    val currentListOfCityData: StateFlow<List<CityModel>?>
        get() = _currentListOfCityData

    private fun findCitiesByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentListOfCityData.emit(cityRepository.searchCityByName(name))
        }
    }
    fun setCityFavorite(name: String, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            cityRepository.setCityFavoriteState(name, isFavorite)
        }
    }

    fun newCity(city: String) {
        WeatherDataHelper.currentCity = city
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.weatherForCity(city)
        }
    }
}
