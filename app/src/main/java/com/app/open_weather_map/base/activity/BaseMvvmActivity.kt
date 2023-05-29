package com.app.open_weather_map.base.activity

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.app.open_weather_map.base.viewmodel.BaseViewModel
import javax.inject.Inject

abstract class BaseMvvmActivity<VB, VM> :
    BaseBindingActivity<VB>() where VB : ViewBinding, VM : BaseViewModel {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    /**
     * Returns the View Model related to the actual activity
     */
    protected abstract val viewModel: VM

    protected inline fun <reified VM : ViewModel> injectedViewModel() =
        viewModels<VM> { viewModelFactory }
}
