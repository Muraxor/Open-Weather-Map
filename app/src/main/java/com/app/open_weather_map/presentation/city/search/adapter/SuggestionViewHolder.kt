package com.app.open_weather_map.presentation.city.search.adapter

import androidx.recyclerview.widget.RecyclerView
import com.app.open_weather_map.R
import com.app.open_weather_map.base.recyclerview.BaseViewHolder
import com.app.open_weather_map.databinding.ItemSearchSuggestionBinding
import com.app.open_weather_map.extnesions.setDisablingClickListener
import com.app.open_weather_map.presentation.city.search.adapter.model.SuggestionUiModel

class SuggestionViewHolder(
    private val binding: ItemSearchSuggestionBinding,
    private val listener: SearchSuggestionsAdapter.ItemClickListener
) : BaseViewHolder<SuggestionUiModel>(binding.root) {

    override fun bind(model: SuggestionUiModel) {
        with(binding) {
            val position = absoluteAdapterPosition

            suggestionName.text = model.name

            favoriteIcon.apply {
                val imageRes = when (model.isFavorite) {
                    true -> R.drawable.ic_favorite_checked
                    false -> R.drawable.ic_favorite_unchecked
                }
                setImageResource(imageRes)
            }

            root.setOnClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(model)
                }
            }
            favoriteIcon.setDisablingClickListener {
                if (position != RecyclerView.NO_POSITION) {
                    listener.onFavoriteClicked(model)
                }
            }
        }
    }
}