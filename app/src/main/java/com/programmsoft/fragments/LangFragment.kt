package com.programmsoft.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.programmsoft.chinesehoroscope.databinding.DialogLangBinding
import com.programmsoft.utils.App
import com.programmsoft.utils.LanguageManager
import com.programmsoft.utils.SharedPreference

class LangFragment : DialogFragment() {
    private val TAG = "DialogFragment"

    interface OnInputListener {
        fun sendInput(input: String?)
    }

    private var mOnInputListener: OnInputListener? = null

    @SuppressLint("UseRequireInsteadOfGet", "UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        SharedPreference.init(App.instance)

        val dialog = Dialog(requireActivity())
        when (tag) {

            "lang" -> {
                val lang = LanguageManager(requireActivity())
                val dialogView =
                    DialogLangBinding.inflate(LayoutInflater.from(requireActivity()), null, false)
                dialog.setContentView(dialogView.root)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                when (SharedPreference.lang) {
                    "uz" -> {
                        dialogView.langUz.isChecked = true
                    }
                    "kr" -> {
                        dialogView.langKr.isChecked = true
                    }
                }
                dialogView.langRadioGroup.setOnCheckedChangeListener { _, i ->
                    when (i) {
                        dialogView.langUz.id -> {
                            SharedPreference.lang = "uz"
                            lang.updateResource("uz")
                            mOnInputListener?.sendInput("uz")
                            dismiss()
                        }
                        dialogView.langKr.id -> {
                            SharedPreference.lang = "kr"
                            lang.updateResource("kr")
                            mOnInputListener?.sendInput("kr")
                            dismiss()
                        }
                    }
                }
                dialog.show()
            }
        }
        return dialog
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mOnInputListener = activity as OnInputListener?
        } catch (e: ClassCastException) {
            Log.e(TAG, "onAttach: ClassCastException: "
                    + e.message)
        }
    }
}