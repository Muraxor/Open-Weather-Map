package com.app.open_weather_map.presentation.city.search

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.app.open_weather_map.base.fragment.BaseMvvmFragment
import com.app.open_weather_map.databinding.FragmentSearchCityBinding
import com.app.open_weather_map.di.AppInjector
import com.app.open_weather_map.extnesions.*
import com.app.open_weather_map.presentation.city.weather.CitySharedViewModel

private typealias ViewBinding = FragmentSearchCityBinding

class SearchCityFragment : BaseMvvmFragment<ViewBinding, SearchCityViewModel>() {

    private val sharedViewModel by activityViewModels<CitySharedViewModel> { viewModelFactory }
    override val viewModel by injectedViewModel<SearchCityViewModel>()

    override fun getBindingInflate(): ViewBindingInflateProvider<ViewBinding> =
        ViewBinding::inflate

    override fun onAttach(context: Context) {
        AppInjector.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        withBinding {
            searchCityButton.setDisablingClickListener {
                sharedViewModel.onCityChosen(cityNameEditText.value)
                popBackStack()
            }
        }
    }
}
