package com.test.unlimitedproduction.weathercity.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.test.unlimitedproduction.weathercity.App
import com.test.unlimitedproduction.weathercity.R
import com.test.unlimitedproduction.weathercity.databinding.FragmentCityBinding
import com.test.unlimitedproduction.weathercity.ui.search.adapter.CityItemAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
class CityFragment : Fragment() {

    private val cityInputListener = object : TextWatcher {
        private var debounceJob: Job? = null
        private val DELAY: Long = 500L

        override fun afterTextChanged(s: Editable?) {
            debounceJob?.cancel()
            debounceJob = this@CityFragment.lifecycleScope
                .launch(Dispatchers.Main) {
                    delay(DELAY)
                    viewModel.onInputChanged(s?.toString() ?: "")
                }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }
    private var _binding: FragmentCityBinding? = null
    val binding: FragmentCityBinding
        get() = _binding ?: throw RuntimeException("Should only use binding after onCreateView and before onDestroyView")

    @Inject
    lateinit var viewModel: CitySearchViewModel
    private val cityItemAdapter = CityItemAdapter { city ->
        viewModel.newCity(city)
        findNavController().popBackStack()
    }

    override fun onAttach(context: Context) {
        App.instance.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_city, container, false)
        _binding = FragmentCityBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etCityForSearch.addTextChangedListener(cityInputListener)
        binding.rvParkingPhotosCurrent.adapter = cityItemAdapter
        lifecycleScope.launch {
            viewModel.currentListOfCityData.collectLatest {
                cityItemAdapter.submitList(it ?: listOf())
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
