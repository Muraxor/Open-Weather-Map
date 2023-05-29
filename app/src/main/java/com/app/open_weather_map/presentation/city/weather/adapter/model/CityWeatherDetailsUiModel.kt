package com.app.open_weather_map.presentation.city.weather.adapter.model

import android.content.Context
import androidx.annotation.DrawableRes
import com.app.open_weather_map.R
import com.app.open_weather_map.data.database.entities.WeatherEntity

data class CityWeatherDetailsUiModel(
    val name: String,
    val value: String,
    @DrawableRes val icon: Int? = null
) {

    companion object {

        fun setupList(
            cityName: String,
            weather: WeatherEntity,
            context: Context
        ): List<CityWeatherDetailsUiModel> {
            val name = context.getString(R.string.city_name_title_text)
            val brandTitle = context.getString(R.string.weather_details_main_title_text)
            val descriptionTitle =
                context.getString(R.string.weather_details_description_title_text)

            return weather.run {
                arrayOf(
                    CityWeatherDetailsUiModel(name, cityName),
                    CityWeatherDetailsUiModel(brandTitle, main),
                    CityWeatherDetailsUiModel(descriptionTitle, description, icon.asDrawableRes()),
                ).filter { it.value.isNotEmpty() }
            }
        }

        private fun String.asDrawableRes(): Int {
            return when (this) {
                "01d" -> R.drawable.clear_sky_01d_icon
                "10d" -> R.drawable.rain_10d_icon
                "03d" -> R.drawable.scattered_clouds_03d_icon
                else -> R.drawable.clear_sky_01d_icon
            }
        }
    }
}
