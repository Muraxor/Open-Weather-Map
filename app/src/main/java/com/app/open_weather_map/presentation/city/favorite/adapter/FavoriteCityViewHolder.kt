package com.app.open_weather_map.presentation.city.favorite.adapter

import androidx.recyclerview.widget.RecyclerView
import com.app.open_weather_map.base.recyclerview.BaseViewHolder
import com.app.open_weather_map.databinding.ItemFavoriteCityBinding
import com.app.open_weather_map.extnesions.setDisablingClickListener
import com.app.open_weather_map.presentation.city.favorite.adapter.model.FavoriteCityUiModel

class FavoriteCityViewHolder(
    private val binding: ItemFavoriteCityBinding,
    private val listener: FavoriteCitiesAdapter.ItemClickListener
) : BaseViewHolder<FavoriteCityUiModel>(binding.root) {

    override fun bind(model: FavoriteCityUiModel) {
        with(binding) {
            val position = absoluteAdapterPosition

            cityNameTextView.text = model.name

            root.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(model)
                }
            }
            deleteCityIcon.setDisablingClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClicked(model)
                }
            }
        }
    }
}
