package com.test.unlimitedproduction.weathercity.ui.weather

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.test.unlimitedproduction.weathercity.App
import com.test.unlimitedproduction.weathercity.R
import com.test.unlimitedproduction.weathercity.databinding.FragmentWeatherBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

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
        initListeners()
    }

    private fun initListeners() {
        lifecycleScope.launch {
            viewModel.currentWeatherData.collectLatest {
                binding.apply {
                    tvTemperatureHolder.text = it?.temp.toString()
                    tvHumidityHolder.text = it?.humidity.toString()
                    tvWindHolder.text = it?.wind.toString()
                    tvCityName.text = it?.city
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
