package com.programmsoft.chinesehoroscope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.programmsoft.chinesehoroscope.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    private val binding: ActivitySplashBinding by viewBinding()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
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