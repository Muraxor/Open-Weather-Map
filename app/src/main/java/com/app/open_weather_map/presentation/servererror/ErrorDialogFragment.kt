package com.app.open_weather_map.presentation.servererror

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.app.open_weather_map.R

class ErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(arguments.title)
            .setMessage(arguments.message)
            .setPositiveButton(getString(android.R.string.ok)) { _, _ -> }
            .create()

    private val Bundle?.title: String
        get() = (this?.getString(TITLE_KEY) ?: getString(R.string.error_text))

    private val Bundle?.message: String
        get() = (this?.getString(MESSAGE_KEY) ?: getString(R.string.something_go_wrong))

    companion object {

        private const val TITLE_KEY = "title_key"
        private const val MESSAGE_KEY = "message_key"

        /**
         * Null is default. Fragment will take default string instead of null
         */
        fun newInstanceWithArgs(
            title: String? = null,
            message: String? = null,
        ): ErrorDialogFragment {
            return ErrorDialogFragment().apply {
                arguments = Bundle().also { args ->
                    args.putString(TITLE_KEY, title)
                    args.putString(MESSAGE_KEY, message)
                }
            }
        }
    }
}
