package com.programmsoft.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.programmsoft.chinesehoroscope.MainActivity
import com.programmsoft.chinesehoroscope.R
import com.programmsoft.chinesehoroscope.databinding.FragmentAboutZodiacBinding
import com.programmsoft.utils.App

class AboutZodiacFragment : Fragment(R.layout.fragment_about_zodiac) {
    private val binding: FragmentAboutZodiacBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getInt("id")
        val zodiac = MainActivity.zodiacList[id!! - 1]
        binding.collapsingToolBar.title = zodiac.name
        binding.zodiacTV.text = zodiac.description
        var drawableStr = "about_" + zodiac.img.toString()
        val imageId = App.instance.resources.getIdentifier(drawableStr.lowercase(),
            "drawable",
            App.instance.packageName)
        binding.zodiacImg.setImageResource(imageId)
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}