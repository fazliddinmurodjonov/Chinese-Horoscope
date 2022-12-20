package com.programmsoft.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.programmsoft.chinesehoroscope.MainActivity
import com.programmsoft.chinesehoroscope.R
import com.programmsoft.chinesehoroscope.databinding.FragmentCompatibilityBinding
import com.programmsoft.utils.App
import com.programmsoft.utils.SharedPreference

class CompatibilityFragment : Fragment(R.layout.fragment_compatibility) {
    private val binding: FragmentCompatibilityBinding by viewBinding()
    var clickMale = true
    var clickFemale = true
    var eventDismiss = 0
    var posMale = -1
    var posFemale = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SharedPreference.init(requireActivity())
        clickBtns()
        getResultFromDialogFragment()
        updateLangTextsFragment()
    }

    private fun clickBtns() {
        binding.imgMale.setOnClickListener {
            if (clickMale) {
                horoscopeDialog("male")
                clickMale = false
            }
        }
        binding.imgFemale.setOnClickListener {
            if (clickFemale) {
                horoscopeDialog("female")
                clickFemale = false
            }
        }
        binding.checkCompBtn.setOnClickListener {
            if (posMale != -1 && posFemale != -1) {
                val compBundle = bundleOf("posOne" to posMale, "posTwo" to posFemale)
                findNavController().navigate(R.id.resultCompatibilityFragment, compBundle)
            }
        }
    }

    private fun getResultFromDialogFragment() {
        requireActivity().supportFragmentManager
            .setFragmentResultListener("requestKey", this) { _, bundle ->
                val gender = bundle.getString("gender")
                val position = bundle.getInt("position")
                eventDismiss = bundle.getInt("eventDismiss")
                if (eventDismiss == -1) {
                    clickMale = true
                    clickFemale = true
                }
                setData(gender, position)
            }
    }

    private fun updateLangTextsFragment() {
        with(binding)
        {
            selectMTv.text = getString(R.string.select_male)
            selectFTv.text = getString(R.string.select_female)
            compatibilityTv.text = getString(R.string.compatibilityBtn)
        }
    }
        private fun horoscopeDialog(langTheme: String) {
        val compatibilityDialogFragment = CompatibilityDialogFragment()
        compatibilityDialogFragment.show(requireActivity().supportFragmentManager, langTheme)
    }

    private fun setData(gender: String?, position: Int?) {
        when (gender) {
            "male" -> {
                val constellations = MainActivity.zodiacList[position!! - 1]
                posMale = position - 1
                val drawableStr = constellations.img
                val imageId = App.instance.resources.getIdentifier(drawableStr!!.lowercase(),
                    "drawable",
                    App.instance.packageName)
                binding.imgMale.setImageResource(imageId)
                binding.constellationMaleTv.text = constellations.name
                clickMale = true
                eventDismiss = 0
                binding.circleInnerMale.setBackgroundResource(R.drawable.background_view_circle_inner)
                binding.selectMTv.visibility = View.INVISIBLE
            }
            "female" -> {
                val constellations = MainActivity.zodiacList[position!! - 1]
                posFemale = position - 1
                val drawableStr = constellations.img
                val imageId = App.instance.resources.getIdentifier(drawableStr!!.lowercase(),
                    "drawable",
                    App.instance.packageName)
                binding.imgFemale.setImageResource(imageId)
                binding.constellationFemaleTv.text = constellations.name
                clickFemale = true
                eventDismiss = 0
                binding.circleInnerFemale.setBackgroundResource(R.drawable.background_view_circle_inner)
                binding.selectFTv.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        posMale = -1
        posFemale = -1
    }
}