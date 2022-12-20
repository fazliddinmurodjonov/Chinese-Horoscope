package com.programmsoft.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.programmsoft.chinesehoroscope.MainActivity
import com.programmsoft.chinesehoroscope.R
import com.programmsoft.chinesehoroscope.databinding.FragmentResultCompatibilityBinding
import com.programmsoft.models.Compatibility
import com.programmsoft.utils.App
import com.programmsoft.utils.SharedPreference
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type
import java.nio.charset.Charset
import kotlin.random.Random
import kotlin.random.nextInt


class ResultCompatibilityFragment : Fragment(R.layout.fragment_result_compatibility) {
    private val binding: FragmentResultCompatibilityBinding by viewBinding()
    private var compatibilitiesList = ArrayList<Compatibility>()
    var resultCompatibility = ""
    var resultPercentage = 0
    val handler = Handler(Looper.getMainLooper())
    var i = 0
    var posOne = 0
    var posTwo = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compatibilitiesList = loadCompatibilityWithLang(SharedPreference.lang!!)
        getResult()
        loadData()
    }
    private fun loadData() {
        resultPercentage = Random.nextInt(70..95)
        handler.postDelayed(runnable, 20)
        var firstConstellation = MainActivity.zodiacList[posOne]
        var secondConstellation = MainActivity.zodiacList[posTwo]
        val firstDrawable = "book_" + firstConstellation.img
        val firstImgId = App.instance.resources.getIdentifier(firstDrawable!!.lowercase(),
            "drawable",
            App.instance.packageName)
        val secondDrawable = "book_" + secondConstellation.img
        val secondImgId = App.instance.resources.getIdentifier(secondDrawable!!.lowercase(),
            "drawable",
            App.instance.packageName)
        with(binding)
        {
            constellationFirstImg.setImageResource(firstImgId)
            constellationSecondImg.setImageResource(secondImgId)
            constellationNameMale.text = firstConstellation.name
            constellationNameFemale.text = secondConstellation.name
            otherPairsBtn.setOnClickListener {
                findNavController().popBackStack()
            }
            overallCompatibilityTv.text = resultCompatibility
        }

    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun getResult() {
        posOne = arguments!!.getInt("posOne")
        posTwo = arguments!!.getInt("posTwo")
        val compatibilities = compatibilitiesList[posOne]
        when (posTwo + 1) {
            1 -> {
                resultCompatibility = compatibilities.rat!!
            }
            2 -> {
                resultCompatibility = compatibilities.ox!!
            }
            3 -> {
                resultCompatibility = compatibilities.tiger!!
            }
            4 -> {
                resultCompatibility = compatibilities.rabbit!!
            }
            5 -> {
                resultCompatibility = compatibilities.dragon!!
            }
            6 -> {
                resultCompatibility = compatibilities.snake!!
            }
            7 -> {
                resultCompatibility = compatibilities.horse!!
            }
            8 -> {
                resultCompatibility = compatibilities.goat!!
            }
            9 -> {
                resultCompatibility = compatibilities.monkey!!
            }
            10 -> {
                resultCompatibility = compatibilities.rooster!!
            }
            11 -> {
                resultCompatibility = compatibilities.dog!!
            }
            12 -> {
                resultCompatibility = compatibilities.pig!!
            }
        }
    }

    private fun loadCompatibilityWithLang(lang: String): ArrayList<Compatibility> {
        var compatibilityList = ArrayList<Compatibility>()
        when (lang) {
            "uz" -> {
                val loadHoroscopeFromAsset = loadJSONFromAsset("compatibility_zodiac_uz")
                compatibilityList = loadCompatibility(loadHoroscopeFromAsset!!)
            }
            "kr" -> {
                val loadHoroscopeFromAsset = loadJSONFromAsset("compatibility_zodiac_kirill")
                compatibilityList = loadCompatibility(loadHoroscopeFromAsset!!)
            }
        }
        return compatibilityList
    }
    private fun loadCompatibility(json: String): ArrayList<Compatibility> {
        val gson = Gson()
        val type: Type = object : TypeToken<ArrayList<Compatibility>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun loadJSONFromAsset(jsonName: String): String? {
        val json: String? = try {
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


    private val runnable = object : Runnable {
        @SuppressLint("SetTextI18n")
        override fun run() {
            if (i <= resultPercentage) {
                binding.percentageTv.text = "$i%"
                i++
                handler.postDelayed(this, 20)
            } else {
                handler.removeCallbacks(this)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}