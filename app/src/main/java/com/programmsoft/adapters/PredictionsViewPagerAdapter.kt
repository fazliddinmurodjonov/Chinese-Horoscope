package com.programmsoft.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.programmsoft.fragments.PredictionsViewPagerFragment

class PredictionsViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    var id: Int,
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return PredictionsViewPagerFragment.newInstance(position, id)
    }
}