package com.app.open_weather_map.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.app.open_weather_map.extnesions.ViewBindingInflateProvider

abstract class BaseBindingFragment<VB> : Fragment() where VB : ViewBinding {

    private var binding: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getBindingInflate().invoke(inflater, container, false)
        return binding!!.root
    }

    protected fun requireBinding(): VB = if (binding == null) {
        throw IllegalStateException("Illegal state to get binding instance")
    } else {
        binding!!
    }

    @Suppress("unused")
    protected fun withBinding(block: VB.() -> Unit) = requireBinding().block()

    protected abstract fun getBindingInflate(): ViewBindingInflateProvider<VB>

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
