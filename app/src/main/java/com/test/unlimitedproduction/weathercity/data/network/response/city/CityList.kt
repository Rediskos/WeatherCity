package com.test.unlimitedproduction.weathercity.data.network.response.city

import com.test.unlimitedproduction.weathercity.domain.model.CityModel

class CityList : ArrayList<CityItem>()

fun CityList.mapToDomain() = map { CityModel(name = it.name) }
