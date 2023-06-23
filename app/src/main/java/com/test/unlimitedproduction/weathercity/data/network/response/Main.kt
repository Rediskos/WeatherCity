package com.test.unlimitedproduction.weathercity.data.network.response

import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("grnd_level") val groundLevel: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("sea_level")val seaLevel: Int,
    @SerializedName("temp") val temp: Double,
    @SerializedName("temp_max")val tempMax: Double,
    @SerializedName("temp_max")val tempMin: Double
)
