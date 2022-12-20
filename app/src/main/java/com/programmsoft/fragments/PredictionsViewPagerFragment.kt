package com.programmsoft.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.programmsoft.chinesehoroscope.databinding.FragmentPredictionsViewPagerBinding
import com.programmsoft.room.entity.ZodiacBase
import com.programmsoft.utils.CreateDB
import com.programmsoft.utils.SharedPreference

private const val POSITION = "position"
private const val ZODIAC_ID = "zodiac_id"

class PredictionsViewPagerFragment : Fragment() {
    private val binding: FragmentPredictionsViewPagerBinding by viewBinding()
    private var position: Int? = null
    private var zodiacId: Int? = null
    val db = CreateDB.db
    val handler = Handler(Looper.getMainLooper())
    var zodiac = ZodiacBase()
    var progressWork = 0
    var progressHealth = 0
    var progressLove = 0
    var workCount = 0
    var healthCount = 0
    var loveCount = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        SharedPreference.init(requireActivity())
        arguments?.let {
            position = it.getInt(POSITION)
            zodiacId = it.getInt(ZODIAC_ID)
        }
        zodiac = db.zodiacBaseDao().getById(zodiacId!!)
        when (SharedPreference.lang) {
            "uz" -> {
                when (position) {
                    0 -> {
                        binding.predictionTv.text = zodiac.todayUzb
                        workCount = zodiac.workToday!!
                        healthCount = zodiac.healthToday!!
                        loveCount = zodiac.loveToday!!
                    }
                    1 -> {
                        binding.predictionTv.text = zodiac.tomorrowUzb
                        workCount = zodiac.workTomorrow!!
                        healthCount = zodiac.healthTomorrow!!
                        loveCount = zodiac.loveTomorrow!!
                    }
                }
            }
            "kr" -> {
                when (position) {
                    0 -> {
                        binding.predictionTv.text = zodiac.todayUzb
                        workCount = zodiac.workToday!!
                        healthCount = zodiac.healthToday!!
                        loveCount = zodiac.loveToday!!
                    }
                    1 -> {
                        binding.predictionTv.text = zodiac.tomorrowUzb
                        workCount = zodiac.workTomorrow!!
                        healthCount = zodiac.healthTomorrow!!
                        loveCount = zodiac.loveTomorrow!!
                    }
                }
            }
        }
        handler.postDelayed(runnableWork, 0)
        handler.postDelayed(runnableHealth, 0)
        handler.postDelayed(runnableLove, 0)
    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int, zodiacId: Int) =
            PredictionsViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                    putInt(ZODIAC_ID, zodiacId)
                }
            }
    }
    private val runnableWork = object : Runnable {
        override fun run() {
            if (progressWork <= workCount) {
                binding.workProgressBar.progress = progressWork
                binding.workCountTv.text = progressWork.toString()
                progressWork++
                handler.postDelayed(this, 5)
            } else {
                handler.removeCallbacks(this)
                progressWork = 0
            }
        }
    }
    private val runnableHealth = object : Runnable {
        override fun run() {
            if (progressHealth <= healthCount) {
                binding.healthProgressBar.progress = progressHealth
                binding.healthCountTv.text = progressHealth.toString()
                progressHealth++
                handler.postDelayed(this, 5)
            } else {
                handler.removeCallbacks(this)
            }
        }
    }
    private val runnableLove = object : Runnable {
        override fun run() {
            if (progressLove <= loveCount) {
                binding.loveProgressBar.progress = progressLove
                binding.loveCountTv.text = progressHealth.toString()
                progressLove++
                handler.postDelayed(this, 5)
            } else {
                handler.removeCallbacks(this)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnableWork)
        handler.removeCallbacks(runnableHealth)
        handler.removeCallbacks(runnableLove)
    }
}