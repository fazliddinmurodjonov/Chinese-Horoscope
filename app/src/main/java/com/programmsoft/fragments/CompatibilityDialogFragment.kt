package com.programmsoft.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.programmsoft.adapters.CompatibilityDialogAdapter
import com.programmsoft.chinesehoroscope.MainActivity
import com.programmsoft.chinesehoroscope.R
import com.programmsoft.chinesehoroscope.databinding.DialogCompatibilityBinding


class CompatibilityDialogFragment : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        when (tag) {
            "male" -> {
                val adapter = CompatibilityDialogAdapter()
                adapter.submitList(MainActivity.zodiacList)
                val dialogView =
                    DialogCompatibilityBinding.inflate(LayoutInflater.from(requireContext()),
                        null,
                        false)
                dialog.setContentView(dialogView.root)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialogView.horoscopeRV.adapter = adapter
                adapter.setOnItemClickListener { position ->
                    dialog.dismiss()
                    setFragmentResult("requestKey",
                        bundleOf("position" to position, "gender" to "male", "eventDismiss" to 1))
                }
                dialog.show()
            }
            "female" -> {
                val adapter = CompatibilityDialogAdapter()
                adapter.submitList(MainActivity.zodiacList)
                val dialogView =
                    DialogCompatibilityBinding.inflate(LayoutInflater.from(requireContext()),
                        null,
                        false)
                dialog.setContentView(dialogView.root)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialogView.horoscopeRV.adapter = adapter
                adapter.setOnItemClickListener { position ->
                    setFragmentResult("requestKey",
                        bundleOf("position" to position, "gender" to "female", "eventDismiss" to 1))

                    dialog.dismiss()
                }
                dialog.show()
            }
        }
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        setFragmentResult("requestKey", bundleOf("eventDismiss" to -1))
    }
}