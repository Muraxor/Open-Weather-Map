package com.app.open_weather_map.extnesions

import android.app.Activity
import android.content.pm.PackageManager
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.app.open_weather_map.BuildConfig

fun Activity.blockUI() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Activity.unblockUI() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Activity.makeAndShowToastDebug(
    text: String,
    duration: Int = Toast.LENGTH_LONG
) {
    if (BuildConfig.DEBUG) {
        Toast.makeText(this, text, duration).show()
    }
}

fun Activity.requirePermissions(
    permissions: Array<String>,
    performIfAllIsGranted: () -> Unit,
    performIfSomeDenied: (Array<String>) -> Unit = {},
) {
    val deniedPermissionIds =
        permissions.filter { (ContextCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_DENIED) }

    if (deniedPermissionIds.isEmpty()) {
        performIfAllIsGranted()
    } else {
        performIfSomeDenied(deniedPermissionIds.toTypedArray())
    }
}
