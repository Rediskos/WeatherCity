package com.test.unlimitedproduction.weathercity.ui.favorite

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.test.unlimitedproduction.weathercity.App
import com.test.unlimitedproduction.weathercity.R
import com.test.unlimitedproduction.weathercity.databinding.FragmentFavoriteBinding
import com.test.unlimitedproduction.weathercity.ui.search.adapter.CityItemAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteFragment: Fragment() {

    @Inject
    lateinit var viewModel: FavoriteViewModel

    private val cityItemAdapter = CityItemAdapter (
        onCityClickListener = { city ->
            viewModel.newCity(city)
            findNavController().popBackStack()
        },
        onSetCityFavoriteClickListener = { city, isFavorite ->
            viewModel.setCityFavorite(city, isFavorite)
        }
    )

    private var _binding: FragmentFavoriteBinding? = null
    val binding: FragmentFavoriteBinding
        get() = _binding ?: throw RuntimeException("Should only use binding after onCreateView and before onDestroyView")

    override fun onAttach(context: Context) {
        App.instance.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        _binding = FragmentFavoriteBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            rvCityList.adapter = cityItemAdapter
            lifecycleScope.launch {
                viewModel.currentListOfCityData.collectLatest {
                    cityItemAdapter.submitList(it ?: listOf())
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
