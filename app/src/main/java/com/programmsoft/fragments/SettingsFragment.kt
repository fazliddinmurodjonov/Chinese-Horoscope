package com.programmsoft.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.programmsoft.chinesehoroscope.R
import com.programmsoft.chinesehoroscope.databinding.FragmentSettingsBinding
import com.programmsoft.utils.SharedPreference

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val binding: FragmentSettingsBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SharedPreference.init(requireActivity())
        clickBtns()
        updateLangTextsFragment()
    }

    private fun clickBtns() {
        binding.cardLang.setOnClickListener {
            langDialog("lang")

        }
        binding.notificationMode.isChecked = SharedPreference.notificationTurnOn == 1

        binding.notificationMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                SharedPreference.notificationTurnOn = 1
            } else {
                SharedPreference.notificationTurnOn = 0
            }
        }

        binding.cardInfo.setOnClickListener {
            aboutAppDialog()
        }
        binding.cardRate.setOnClickListener {
            val uri: Uri =
                Uri.parse("https://play.google.com/store/apps/details?id=" + requireActivity().packageName)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        binding.cardOtherApps.setOnClickListener {
            val url = "https://play.google.com/store/apps/dev?id=5705777915421890833"
            val uriUrl: Uri = Uri.parse(url)
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            startActivity(launchBrowser)
        }
        binding.cardShare.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,
                    getString(R.string.chinese_horoscope) +
                            "\n" +
                            "\uD83D\uDC47" +
                            getString(R.string.downloadApp) +
                            "\uD83D\uDC47\n" +
                            "https://play.google.com/store/apps/details?id=" + requireActivity().packageName)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun langDialog(langTheme: String) {
        val langThemeFragment = LangFragment()
        langThemeFragment.show(requireActivity().supportFragmentManager, langTheme)
    }

    private fun updateLangTextsFragment() {
        binding.langTv.text = getString(R.string.lang)
        binding.langSelectedTv.text = getString(R.string.lang_type)
        binding.appInfoTv.text = getString(R.string.infoApp)
        binding.appRateTv.text = getString(R.string.rateApp)
        binding.otherAppsTv.text = getString(R.string.otherApps)
        binding.shareAppTv.text = getString(R.string.shareApp)
        binding.appBarLayout.text = getString(R.string.settings)
    }


    private fun aboutAppDialog() {
        val aboutProgramFragment = AboutProgramFragment()
        aboutProgramFragment.show(requireActivity().supportFragmentManager, "About Program Dialog")
    }
}