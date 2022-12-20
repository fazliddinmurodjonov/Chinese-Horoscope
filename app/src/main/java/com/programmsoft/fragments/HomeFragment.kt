package com.programmsoft.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.programmsoft.adapters.ZodiacAdapter
import com.programmsoft.chinesehoroscope.MainActivity
import com.programmsoft.chinesehoroscope.R
import com.programmsoft.chinesehoroscope.databinding.FragmentHomeBinding
import com.programmsoft.models.Zodiac
import com.programmsoft.utils.CreateDB
import com.programmsoft.utils.SharedPreference
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type
import java.nio.charset.Charset


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()
    private var zodiacList = ArrayList<Zodiac>()
    private lateinit var adapter: ZodiacAdapter
    private val db = CreateDB.db
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SharedPreference.init(requireActivity())
        updateLang()
        uploadRecycler()
    }

    private fun updateLang() {
        binding.horoscopeTV.setText(R.string.chinese_horoscope)
    }

    private fun uploadRecycler() {
        zodiacList = loadHoroscopeWithLang(SharedPreference.lang!!)
        binding.horoscopeRV.viewTreeObserver.addOnPreDrawListener(object :
            ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                if (binding.horoscopeRV.viewTreeObserver.isAlive) binding.horoscopeRV.viewTreeObserver.removeOnPreDrawListener(
                    this)
                val width = binding.horoscopeRV.width.toFloat() / 5
                val height = binding.horoscopeRV.height.toFloat() / 5
                adapter = ZodiacAdapter(width, height)
                adapter.submitList(MainActivity.zodiacList)
                binding.horoscopeRV.adapter = adapter
                adapter.setOnItemClickListener {
                    if (db.zodiacBaseDao().getAll().isNotEmpty()) {
                        val bundleOf = bundleOf("id" to it)
                        findNavController().navigate(R.id.predictionsFragment, bundleOf)
                    }

                }
                return true
            }
        })
    }

    private fun loadHoroscopeWithLang(lang: String): ArrayList<Zodiac> {
        var zodiacList = ArrayList<Zodiac>()
        when (lang) {
            "uz" -> {
                val loadHoroscopeFromAsset = loadJSONFromAsset("zodiac_uzbek")
                zodiacList = loadHoroscope(loadHoroscopeFromAsset!!)
            }

            "kr" -> {
                val loadHoroscopeFromAsset = loadJSONFromAsset("zodiac_kirill")
                zodiacList = loadHoroscope(loadHoroscopeFromAsset!!)
            }
        }
        return zodiacList
    }

    private fun loadHoroscope(json: String): ArrayList<Zodiac> {
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<Zodiac>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun loadJSONFromAsset(jsonName: String): String? {
        var json: String? = try {
            val inputStream: InputStream = requireActivity().assets.open("$jsonName.json")
            val sizeOfFile = inputStream.available()
            val bufferDate = ByteArray(sizeOfFile)
            inputStream.read(bufferDate)
            inputStream.close()
            String(bufferDate, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }
}