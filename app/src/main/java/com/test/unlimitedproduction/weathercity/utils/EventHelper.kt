package com.test.unlimitedproduction.weathercity.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object EventHelper {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    suspend fun showLoader() {
        _isLoading.emit(true)
    }

    suspend fun hideLoader() {
        _isLoading.emit(false)
    }
}
