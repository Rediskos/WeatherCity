package com.test.unlimitedproduction.weathercity.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.unlimitedproduction.weathercity.R
import com.test.unlimitedproduction.weathercity.databinding.ItemCityBinding
import com.test.unlimitedproduction.weathercity.domain.model.CityModel

class CityItemAdapter(
    val onCityClickListener: (city: String) -> Unit,
    val onSetCityFavoriteClickListener: (city: String, isFavorite: Boolean) -> Unit
): ListAdapter<CityModel, CityItemAdapter.CityItemViewHolder>(CityItemDiffCalc()) {
    class CityItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ItemCityBinding.bind(view)
    }

    class CityItemDiffCalc : DiffUtil.ItemCallback<CityModel>() {
        override fun areItemsTheSame(oldItem: CityModel, newItem: CityModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CityModel, newItem: CityModel): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityItemViewHolder, position: Int) {
        val city = getItem(position)

        holder.binding.apply {
            tvCityName.text = city.name
            tvCityName.setOnClickListener {
                onCityClickListener(city.name)
            }
            //TODO checkbox
        }
    }
}
