package com.test.unlimitedproduction.weathercity.ui.weather

import android.content.Context
import android.location.Criteria
import android.location.LocationManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.test.unlimitedproduction.weathercity.App
import com.test.unlimitedproduction.weathercity.R
import com.test.unlimitedproduction.weathercity.databinding.FragmentWeatherBinding
import com.test.unlimitedproduction.weathercity.utils.WeatherDataHelper
import com.test.unlimitedproduction.weathercity.utils.checkLocationPermissionMissing
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEFAULT_WEATHER_ICON = "10n"
class WeatherFragment : Fragment() {

    @Inject lateinit var viewModel: WeatherViewModel

    private var _binding: FragmentWeatherBinding? = null
    val binding: FragmentWeatherBinding
        get() = _binding ?: throw RuntimeException("Should only use binding after onCreateView and before onDestroyView")

    override fun onAttach(context: Context) {
        App.instance.appComponent.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        _binding = FragmentWeatherBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ibSearchCity.setOnClickListener {
            findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToCityFragment())
        }
        binding.ibFavoriteList.setOnClickListener {
            findNavController().navigate(WeatherFragmentDirections.actionWeatherFragmentToFavoriteFragment())
        }
        initListeners()
        if (!requireActivity().checkLocationPermissionMissing()) {
            saveCurrentLocation()
        } else {
            val locationPermissionRequest = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
                val permissionGranted = when {
                    permissions.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        true
                    }
                    permissions.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        true
                    }
                    else -> {
                        false
                    }
                }
                if (permissionGranted) saveCurrentLocation()
            }
            locationPermissionRequest.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun initListeners() {
        lifecycleScope.launch {
            viewModel.currentWeatherData.collectLatest {
                binding.apply {
                    tvTemperatureHolder.text = it?.temp.toString()
                    tvHumidityHolder.text = it?.humidity.toString()
                    tvWindHolder.text = it?.wind.toString()
                    tvCityName.text = it?.city
                    cbIsCityFavorite.apply {
                        isChecked = it?.isFavorite ?: false
                        setOnCheckedChangeListener { _, isFavorite ->
                            it?.city?.let {
                                viewModel.setCityIsFavoriteState(it, isFavorite)
                            }
                        }
                    }
                    Glide.with(this@WeatherFragment)
                        .load(getString(R.string.weather_icon_blueprint_url, it?.icon ?: DEFAULT_WEATHER_ICON))
                        .into(ivWeatherIcon)
                }
            }
        }
    }

    private fun saveCurrentLocation() {
        val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val provider = locationManager.getBestProvider(Criteria(), false)
        if (provider != null)
            locationManager
                .getLastKnownLocation(provider)?.let{
                    WeatherDataHelper.lastKnownLocation = it
                }
        viewModel.checkWeatherForLocation(WeatherDataHelper.lastKnownLocation)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
