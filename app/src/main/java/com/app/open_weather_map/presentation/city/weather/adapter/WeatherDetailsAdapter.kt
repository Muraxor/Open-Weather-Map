package com.app.open_weather_map.presentation.city.weather.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.app.open_weather_map.databinding.ItemWeatherDetailBinding
import com.app.open_weather_map.extnesions.inflateWith
import com.app.open_weather_map.presentation.city.weather.adapter.model.CityWeatherDetailsUiModel

class WeatherDetailsAdapter :
    ListAdapter<CityWeatherDetailsUiModel, ItemDetailViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDetailViewHolder {
        val binding = parent.inflateWith(ItemWeatherDetailBinding::inflate)
        return ItemDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemDetailViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<CityWeatherDetailsUiModel>() {
        override fun areItemsTheSame(
            oldItem: CityWeatherDetailsUiModel, newItem: CityWeatherDetailsUiModel
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: CityWeatherDetailsUiModel, newItem: CityWeatherDetailsUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
