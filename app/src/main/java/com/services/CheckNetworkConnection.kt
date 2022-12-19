package com.programmsoft.services

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.programmsoft.chinesehoroscope.databinding.DialogConnectionBinding
import com.programmsoft.utils.App
import com.programmsoft.utils.NetworkHelper
import com.programmsoft.utils.SharedPreference

class CheckNetworkConnection : BroadcastReceiver() {
    companion object CheckConnect {
        var connect: Int? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        val networkHelper = NetworkHelper(context)
        SharedPreference.init(App.instance)
        connect = if (networkHelper.isNetworkConnected()) {
            1
        } else {
            0
        }
        if (SharedPreference.updateDB != 1) {
            val dialog = Dialog(context)
            if (networkHelper.isNetworkConnected()) {
                dialog.dismiss()
            } else {
                val dialogView =
                    DialogConnectionBinding.inflate(LayoutInflater.from(context), null, false)
                dialog.setContentView(dialogView.root)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialog.setCanceledOnTouchOutside(false)
                dialogView.connectionBtn.setOnClickListener {
                    if (NetworkHelper(context).isNetworkConnected()) {
                        dialog.dismiss()
                    }
                }
                dialog.setCancelable(false)
                dialog.show()
            }
        }

    }
}