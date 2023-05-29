package com.app.open_weather_map.presentation.city.favorite.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.app.open_weather_map.databinding.ItemFavoriteCityBinding
import com.app.open_weather_map.extnesions.inflateWith
import com.app.open_weather_map.presentation.city.favorite.adapter.model.FavoriteCityUiModel

class FavoriteCitiesAdapter(private val listener: ItemClickListener) :
    ListAdapter<FavoriteCityUiModel, FavoriteCityViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteCityViewHolder {
        val binding = parent.inflateWith(ItemFavoriteCityBinding::inflate)
        return FavoriteCityViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: FavoriteCityViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<FavoriteCityUiModel>() {
        override fun areItemsTheSame(
            oldItem: FavoriteCityUiModel, newItem: FavoriteCityUiModel
        ): Boolean {

            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: FavoriteCityUiModel, newItem: FavoriteCityUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface ItemClickListener {
        fun onItemClicked(model: FavoriteCityUiModel)

        fun onDeleteClicked(model: FavoriteCityUiModel)
    }
}
