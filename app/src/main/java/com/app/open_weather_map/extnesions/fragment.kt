package com.app.open_weather_map.extnesions

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import com.google.android.material.R.id.design_bottom_sheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@Suppress("unused")
inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

@Suppress("unused")
inline fun <reified T : Parcelable> Fragment.tryGetArgument(
    block: Bundle?.() -> T?
): T {
    return this.arguments?.block() ?: throw IllegalArgumentException("Argument not found")
}

fun Fragment.showToast(text: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), text, duration).show()
}

fun Fragment.showToast(@StringRes textResource: Int, duration: Int = Toast.LENGTH_LONG) {
    showToast(getString(textResource), duration)
}

/**
 * Call in onCreateView callback
 */
fun BottomSheetDialogFragment.fullScreenSize() {
    fun FrameLayout.setFullSize() {
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        requestLayout()
    }

    dialog?.setOnShowListener { dialog ->
        val layout: FrameLayout? =
            (dialog as BottomSheetDialog).findViewById(design_bottom_sheet)
        layout?.let {
            BottomSheetBehavior.from(it).apply {
                expanded()
                isDraggable = false
                isHideable = false
                skipCollapsed = true
            }
            it.setFullSize()
        }
    }
}

fun Fragment.addLifecycleObservers(vararg observers: DefaultLifecycleObserver) {
    observers.forEach {
        lifecycle.addObserver(it)
    }
}
