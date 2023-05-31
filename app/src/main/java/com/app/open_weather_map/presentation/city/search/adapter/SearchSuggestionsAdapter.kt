package com.app.open_weather_map.presentation.city.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.app.open_weather_map.databinding.ItemSearchSuggestionBinding
import com.app.open_weather_map.extnesions.inflateWith
import com.app.open_weather_map.presentation.city.search.adapter.model.SuggestionUiModel

class SearchSuggestionsAdapter(private val listener: ItemClickListener) :
    ListAdapter<SuggestionUiModel, SuggestionViewHolder>(DiffCallback()) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val binding = parent.inflateWith(ItemSearchSuggestionBinding::inflate)
        return SuggestionViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<SuggestionUiModel>() {
        override fun areItemsTheSame(
            oldItem: SuggestionUiModel, newItem: SuggestionUiModel
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: SuggestionUiModel, newItem: SuggestionUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface ItemClickListener {

        fun onItemClicked(model: SuggestionUiModel)

        fun onFavoriteClicked(model: SuggestionUiModel)
    }
}
