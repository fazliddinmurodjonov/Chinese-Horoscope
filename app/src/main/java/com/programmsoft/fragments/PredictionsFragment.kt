package com.programmsoft.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.programmsoft.adapters.PredictionsViewPagerAdapter
import com.programmsoft.chinesehoroscope.MainActivity
import com.programmsoft.chinesehoroscope.R
import com.programmsoft.chinesehoroscope.databinding.FragmentPredictionsBinding
import com.programmsoft.chinesehoroscope.databinding.ItemTabLayoutBinding
import com.programmsoft.utils.App

class PredictionsFragment : Fragment(R.layout.fragment_predictions) {
    private val binding: FragmentPredictionsBinding by viewBinding()
    private var predictionViewPagerAdapter: PredictionsViewPagerAdapter? = null
    private var zodiacId = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUI()
        loadViewPager()
        clickBtns()
    }

    private fun loadViewPager() {
        predictionViewPagerAdapter =
            PredictionsViewPagerAdapter(requireActivity(), zodiacId)
        binding.viewPager.adapter = predictionViewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ ->
        }.attach()
        setTab()

    }

    private fun setTab() {
        val itemToday =
            ItemTabLayoutBinding.inflate(LayoutInflater.from(requireActivity()),
                null,
                false)
        val tabAtToday = binding.tabLayout.getTabAt(0)
        tabAtToday?.customView = itemToday.root
        itemToday.tabTv.text = getString(R.string.today)
        val itemTomorrow =
            ItemTabLayoutBinding.inflate(LayoutInflater.from(requireActivity()),
                null,
                false)
        val tabAtTomorrow = binding.tabLayout.getTabAt(1)
        tabAtTomorrow?.customView = itemTomorrow.root
        itemTomorrow.tabTv.text = getString(R.string.tomorrow)

    }


    private fun loadUI() {
        zodiacId = requireArguments().getInt("id")
        binding.collapsingToolBar.title = MainActivity.zodiacList[zodiacId - 1].name
        binding.zodiacImg.setImageResource(getImgId(MainActivity.zodiacList[zodiacId - 1].img!!))

    }

    private fun getImgId(name: String): Int {
        val drawableStr = "about_$name"
        return App.instance.resources.getIdentifier(drawableStr.lowercase(),
            "drawable",
            App.instance.packageName)
    }

    private fun clickBtns() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}