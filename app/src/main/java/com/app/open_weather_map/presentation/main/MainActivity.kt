package com.app.open_weather_map.presentation.main

import android.Manifest
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.open_weather_map.base.activity.BaseMvvmActivity
import com.app.open_weather_map.data.network.exceptions.*
import com.app.open_weather_map.databinding.ActivityMainBinding
import com.app.open_weather_map.extnesions.*
import com.app.open_weather_map.presentation.servererror.ErrorDialogFragment
import com.app.open_weather_map.di.AppInjector

class MainActivity : BaseMvvmActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var getUserLocation: ActivityResultLauncher<Array<String>>

    override val viewModel by injectedViewModel<MainViewModel>()

    override fun getBindingInflate(): ActivityViewBindingInflateProvider<ActivityMainBinding> =
        ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        AppInjector.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        observeHttpErrors()
        observeProgressBarState()
        registerForActivityResults()
        checkLocationPermissions()
    }

    // FIXME: catch parent like DisplayException
    private fun observeHttpErrors() {
        viewModel.httpExceptionsLiveData.observe(this) {
            when (it) {
                is ServerTimeoutException,
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

    private fun registerForActivityResults() {
        getUserLocation =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                viewModel.getLocation()
            }
    }

    private fun checkLocationPermissions() {
        requirePermissions(
            permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            performIfAllIsGranted = {
                viewModel.getLocation()
            },
            performIfSomeDenied = { denied ->
                getUserLocation.launch(denied)
            }
        )
    }
}
