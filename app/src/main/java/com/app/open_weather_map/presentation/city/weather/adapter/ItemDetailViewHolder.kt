package com.app.open_weather_map.presentation.city.weather.adapter

import androidx.core.content.ContextCompat
import com.app.open_weather_map.R
import com.app.open_weather_map.base.recyclerview.BaseViewHolder
import com.app.open_weather_map.databinding.ItemWeatherDetailBinding
import com.app.open_weather_map.presentation.city.weather.adapter.model.CityWeatherDetailsUiModel

class ItemDetailViewHolder(
    private val binding: ItemWeatherDetailBinding,
) : BaseViewHolder<CityWeatherDetailsUiModel>(binding.root) {

    override fun bind(model: CityWeatherDetailsUiModel) {
        with(binding.detailTextView) {
            text = viewContext.getString(
                R.string.item_detail_view_holder_pattern,
                model.name,
                model.value
            )

            if (model.icon != null) {
                val drawable = ContextCompat.getDrawable(viewContext, model.icon)
                setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
            } else {
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }
        }
    }
}
