package com.programmsoft.chinesehoroscope

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.programmsoft.chinesehoroscope.databinding.ActivitySplashBinding
import com.programmsoft.chinesehoroscope.databinding.DialogLangBinding
import com.programmsoft.utils.LanguageManager
import com.programmsoft.utils.SharedPreference

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    private val binding: ActivitySplashBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreference.init(this)
        hideStatusBarNavigationBar()
        uploadUI()
        updateLang()
    }

    private fun updateLang() {
        val lang = LanguageManager(this)
        lang.updateResource(SharedPreference.lang!!)
    }

    private fun uploadUI() {
        SharedPreference.init(this)
        Handler(Looper.getMainLooper()).postDelayed({
            if (SharedPreference.selectLanguage == 1) {
                langThemeDialog()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)
    }

    private fun langThemeDialog() {
        val dialog = Dialog(this)
        val lang = LanguageManager(this)
        val dialogView =
            DialogLangBinding.inflate(LayoutInflater.from(this), null, false)
        dialog.setContentView(dialogView.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialogView.langRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                dialogView.langUz.id -> {
                    SharedPreference.lang = "uz"
                    lang.updateResource("uz")
                    dialog.dismiss()
                    SharedPreference.selectLanguage = 0
                    openActivity()
                }
                dialogView.langKr.id -> {
                    SharedPreference.lang = "kr"
                    lang.updateResource("kr")
                    dialog.dismiss()
                    SharedPreference.selectLanguage = 0
                    openActivity()
                }
            }
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.window!!.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        or View.SYSTEM_UI_FLAG_IMMERSIVE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
        dialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        dialog.show()
    }

    private fun openActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun hideStatusBarNavigationBar() {
        val rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_anim)
        binding.chineseHoroscopeImg.animation = rotate
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}