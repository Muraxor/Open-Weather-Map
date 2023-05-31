package com.app.open_weather_map.presentation.city.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.open_weather_map.base.fragment.BaseMvvmFragment
import com.app.open_weather_map.databinding.FragmentSearchCityBinding
import com.app.open_weather_map.di.AppInjector
import com.app.open_weather_map.extnesions.ViewBindingInflateProvider
import com.app.open_weather_map.extnesions.popBackStack
import com.app.open_weather_map.presentation.city.search.adapter.SearchSuggestionsAdapter
import com.app.open_weather_map.presentation.city.search.adapter.model.SuggestionUiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private typealias ViewBinding = FragmentSearchCityBinding

class SearchCityFragment : BaseMvvmFragment<ViewBinding, SearchCityViewModel>(),
    SearchSuggestionsAdapter.ItemClickListener {

    private val searchAdapter = SearchSuggestionsAdapter(this)
    override val viewModel by injectedViewModel<SearchCityViewModel>()

    override fun getBindingInflate(): ViewBindingInflateProvider<ViewBinding> =
        ViewBinding::inflate

    override fun onAttach(context: Context) {
        AppInjector.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCityWeather()

        viewModel.searchCompleteLiveData.observe(viewLifecycleOwner) {
            popBackStack()
        }

        withBinding {
            searchView.editText.doOnTextChanged { text, start, before, count ->
                viewModel.setNameQuery(text.toString())
            }
            searchView
                .editText
                .setOnEditorActionListener { v, actionId, event ->
                    if (actionId == IME_ACTION_SEARCH) {
                        viewModel.completeSearch(searchView.text.toString())
                    }
                    false
                }
        }
    }

    private fun setupCityWeather() = with(requireBinding().suggestionsRecyclerView) {
        layoutManager = LinearLayoutManager(context)
        adapter = searchAdapter

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.citiesSuggestionsFlow().collectLatest {
                    searchAdapter.submitList(it)
                }
            }
        }
    }

    override fun onItemClicked(model: SuggestionUiModel) {
        viewModel.completeSearch(model.name)
    }

    override fun onFavoriteClicked(model: SuggestionUiModel) {
        viewModel.oppositeFavoriteStatus(model)
    }
}
