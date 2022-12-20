package com.programmsoft.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.programmsoft.chinesehoroscope.R
import com.programmsoft.chinesehoroscope.databinding.DialogAboutProgramBinding

class AboutProgramFragment : DialogFragment() {
    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val dialogView =
            DialogAboutProgramBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        dialog.setContentView(dialogView.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogView.webSite.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.programmsoft.uz"))
            startActivity(intent)
        }
        dialogView.mail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            intent.type = "message/rfc822"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("manager@programmsoft.uz"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "subject of email")
            intent.putExtra(Intent.EXTRA_TEXT, "body of email")
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
            }
        }
        dialog.show()
        return dialog
    }
}