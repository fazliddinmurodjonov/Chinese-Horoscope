package com.programmsoft.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.programmsoft.adapters.ZodiacBookAdapter
import com.programmsoft.chinesehoroscope.MainActivity
import com.programmsoft.chinesehoroscope.R
import com.programmsoft.chinesehoroscope.databinding.FragmentBookBinding
import com.programmsoft.utils.SharedPreference

class BookFragment : Fragment(R.layout.fragment_book) {
    private val binding: FragmentBookBinding by viewBinding()
    var zodiacBookAdapter = ZodiacBookAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        SharedPreference.init(requireActivity())
        binding.infoTV.setText(R.string.about_zodiacs)
        uploadRecycler()
    }

    private fun uploadRecycler() {
        val horoscopeList = MainActivity.zodiacList
        zodiacBookAdapter.submitList(horoscopeList)
        binding.bookHoroscopeRV.adapter = zodiacBookAdapter
        zodiacBookAdapter.setOnItemClickListener {
            val bundleOf = bundleOf("id" to it)
            findNavController().navigate(R.id.aboutZodiacFragment, bundleOf)
        }
    }
}