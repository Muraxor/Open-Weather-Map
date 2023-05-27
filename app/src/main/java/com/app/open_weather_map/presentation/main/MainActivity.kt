package com.app.open_weather_map.presentation.main

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.open_weather_map.base.activity.BaseMvvmActivity
import com.app.open_weather_map.data.network.exceptions.*
import com.app.open_weather_map.databinding.ActivityMainBinding
import com.app.open_weather_map.extnesions.*
import com.app.open_weather_map.presentation.servererror.ErrorDialogFragment
import com.app.open_weather_map.di.AppInjector

class MainActivity : BaseMvvmActivity<ActivityMainBinding, MainViewModel>() {

    override val viewModel by injectedViewModel<MainViewModel>()

    override fun getBindingInflate(): ActivityViewBindingInflateProvider<ActivityMainBinding> =
        ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        AppInjector.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        observeHttpErrors()
        observeProgressBarState()
    }

    // FIXME: catch parent like DisplayException
    private fun observeHttpErrors() {
        viewModel.httpExceptionsLiveData.observe(this) {
            when (it) {
                is ServerTimeoutException,
                is AbsentNetworkConnectionException,
                is UnauthorizedException,
                is ConnectionRefusedException -> ErrorDialogFragment.newInstanceWithArgs(
                    message = it.message
                ).show(
                    supportFragmentManager,
                    ErrorDialogFragment::class.java.simpleName
                )
                else -> makeAndShowToastDebug(it.toString())
            }
        }
    }

    private fun observeProgressBarState() {
        viewModel.progressBarStateLiveData.observe(this) { isShowing ->
            with(requireBinding().curtainView.root) {
                if (isShowing) {
                    blockUI()
                    show()
                } else {
                    hide()
                    unblockUI()
                }
            }
        }
    }
}
