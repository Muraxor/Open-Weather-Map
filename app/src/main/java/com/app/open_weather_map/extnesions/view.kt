package com.app.open_weather_map.extnesions

import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.view.postDelayed
import androidx.core.widget.TextViewCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

val EditText.value: String
    get() = text.toString()

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun CheckBox.opposite() {
    isChecked = !isChecked
}

// TODO: remove delay
fun View.setDisablingClickListener(delay: Long = 500, perform: (View) -> Unit) {
    setOnClickListener {
        isEnabled = false
        perform(it)
        postDelayed(delay) {
            isEnabled = true
        }
    }
}

@Suppress("unused")
fun TextView.precomputeAndSetText(
    text: String
) {
    CoroutineScope(Dispatchers.Default).launch {
        val params = TextViewCompat.getTextMetricsParams(this@precomputeAndSetText)
        val precomputedText =
            PrecomputedTextCompat.create(text, params)

        withContext(Dispatchers.Main) {
            TextViewCompat.setPrecomputedText(this@precomputeAndSetText, precomputedText)
        }
    }
}

fun <T : View> BottomSheetBehavior<T>.hidden() {
    isHideable = true
    state = BottomSheetBehavior.STATE_HIDDEN
}

fun <T : View> BottomSheetBehavior<T>.expanded() {
    isHideable = false
    state = BottomSheetBehavior.STATE_EXPANDED
}
