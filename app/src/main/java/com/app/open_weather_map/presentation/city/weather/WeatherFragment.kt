package com.app.open_weather_map.presentation.city.weather

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.OVER_SCROLL_NEVER
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.open_weather_map.R
import com.app.open_weather_map.base.fragment.BaseMvvmFragment
import com.app.open_weather_map.databinding.FragmentWeatherBinding
import com.app.open_weather_map.di.AppInjector
import com.app.open_weather_map.extnesions.ViewBindingInflateProvider
import com.app.open_weather_map.extnesions.navigate
import com.app.open_weather_map.extnesions.setDisablingClickListener
import com.app.open_weather_map.presentation.city.weather.adapter.WeatherDetailsAdapter

private typealias ViewBinding = FragmentWeatherBinding

class WeatherFragment : BaseMvvmFragment<ViewBinding, WeatherViewModel>() {

    private val weatherDetailsAdapter = WeatherDetailsAdapter()
    private val sharedViewModel by activityViewModels<CitySharedViewModel> { viewModelFactory }

    override val viewModel by injectedViewModel<WeatherViewModel>()

    override fun onAttach(context: Context) {
        AppInjector.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun getBindingInflate(): ViewBindingInflateProvider<ViewBinding> =
        ViewBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchButton()
        setupFavoriteCitiesButton()
        setupFavoriteIcon()
        setupDetailsList()
    }

    private fun setupSearchButton() {
        requireBinding().searchCityButton.setDisablingClickListener {
            navigate(R.id.action_weatherFragment_to_searchCityFragment)
        }
    }

    private fun setupFavoriteCitiesButton() {
        requireBinding().favoriteCitiesButton.setDisablingClickListener {
            navigate(R.id.action_weatherFragment_to_favoriteCitiesListFragment)
        }
    }

    private fun setupFavoriteIcon() = with(requireBinding().favoriteIcon) {
        setDisablingClickListener {
            viewModel.oppositeFavoriteStatus()
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) {
            val imageRes = when (it) {
                true -> R.drawable.ic_favorite_checked
                false -> R.drawable.ic_favorite_unchecked
            }
            setImageResource(imageRes)
        }
    }

    private fun setupDetailsList() = with(requireBinding().weatherDetailsList) {
        layoutManager = LinearLayoutManager(context)
        overScrollMode = OVER_SCROLL_NEVER
        adapter = weatherDetailsAdapter

        sharedViewModel.weatherDetails.observe(viewLifecycleOwner) {
            weatherDetailsAdapter.submitList(it)
        }
    }
}
