package com.test.unlimitedproduction.weathercity.ui.search

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

class CitySearchViewModel @Inject constructor(
    val cityRepository: CityRepository, val weatherRepository: WeatherRepository): ViewModel(){
    fun onInputChanged(name: String) {
        findCitiesByName(name)
    }

    private val _currentListOfCityData: MutableStateFlow<List<CityModel>?> = MutableStateFlow(null)
    val currentListOfCityData: StateFlow<List<CityModel>?>
        get() = _currentListOfCityData

    init {

    }

    private fun findCitiesByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentListOfCityData.emit(cityRepository.searchCityByName(name))
        }
    }

    fun newCity(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.weatherForCity(city)
        }
    }


}
