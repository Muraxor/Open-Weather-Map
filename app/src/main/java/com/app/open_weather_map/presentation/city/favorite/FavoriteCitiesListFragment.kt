package com.app.open_weather_map.presentation.city.favorite

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.open_weather_map.base.fragment.BaseMvvmFragment
import com.app.open_weather_map.databinding.FragmentFavoriteCitiesListBinding
import com.app.open_weather_map.di.AppInjector
import com.app.open_weather_map.extnesions.ViewBindingInflateProvider
import com.app.open_weather_map.extnesions.popBackStack
import com.app.open_weather_map.presentation.city.favorite.adapter.FavoriteCitiesAdapter
import com.app.open_weather_map.presentation.city.favorite.adapter.model.FavoriteCityUiModel

private typealias ViewBinding = FragmentFavoriteCitiesListBinding

class FavoriteCitiesListFragment : BaseMvvmFragment<ViewBinding, FavoriteCitiesListViewModel>(),
    FavoriteCitiesAdapter.ItemClickListener {

    private val favoriteCitiesAdapter = FavoriteCitiesAdapter(this)

    override val viewModel by injectedViewModel<FavoriteCitiesListViewModel>()

    override fun getBindingInflate(): ViewBindingInflateProvider<ViewBinding> =
        ViewBinding::inflate

    override fun onAttach(context: Context) {
        AppInjector.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFavoritesList()

        viewModel.noFavorites.observe(viewLifecycleOwner) {
            requireBinding().emptyMessageTextView.isVisible = it
        }

        viewModel.cityClickedLiveData.observe(viewLifecycleOwner) {
            popBackStack()
        }
    }

    private fun setupFavoritesList() = with(requireBinding().favoritesListRecyclerView) {
        layoutManager = LinearLayoutManager(context)
        adapter = favoriteCitiesAdapter

        viewModel.favoritesListLiveData.observe(viewLifecycleOwner) {
            favoriteCitiesAdapter.submitList(it)
        }
    }

    override fun onItemClicked(model: FavoriteCityUiModel) {
        viewModel.onCityClicked(model.name)
    }

    override fun onDeleteClicked(model: FavoriteCityUiModel) {
        viewModel.deleteFromFavorites(model.name)
    }
}
