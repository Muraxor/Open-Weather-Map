package com.app.open_weather_map.base.fragment

import androidx.viewbinding.ViewBinding
import com.app.open_weather_map.base.viewmodel.BaseViewModel

abstract class BaseMvvmFragment<VB, VM> :
    BaseBindingFragment<VB>() where VB : ViewBinding, VM : BaseViewModel {

    /**
     * Returns the View Model related to the actual fragment
     */
    protected abstract val viewModel: VM
}
