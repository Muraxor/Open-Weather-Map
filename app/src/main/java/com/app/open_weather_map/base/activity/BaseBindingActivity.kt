package com.app.open_weather_map.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.app.open_weather_map.extnesions.ActivityViewBindingInflateProvider

abstract class BaseBindingActivity<VB> : AppCompatActivity() where VB : ViewBinding {
    private var binding: VB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getBindingInflate().invoke(layoutInflater)
        setContentView(binding!!.root)
    }

    protected fun requireBinding(): VB = if (binding == null) {
        throw IllegalStateException("Illegal state to get binding instance")
    } else {
        binding!!
    }

    @Suppress("unused")
    protected fun withBinding(block: VB.() -> Unit) = requireBinding().block()

    protected abstract fun getBindingInflate(): ActivityViewBindingInflateProvider<VB>

}
