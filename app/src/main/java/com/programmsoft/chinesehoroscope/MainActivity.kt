package com.programmsoft.chinesehoroscope

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.review.ReviewManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.programmsoft.chinesehoroscope.databinding.ActivityMainBinding
import com.programmsoft.fragments.LangFragment
import com.programmsoft.models.Percentages
import com.programmsoft.models.Zodiac
import com.programmsoft.services.CheckNetworkConnection
import com.programmsoft.services.UpdateDBService
import com.programmsoft.utils.*
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity(R.layout.activity_main), LangFragment.OnInputListener {
    private val binding: ActivityMainBinding by viewBinding()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private var checkNetworkConnection = CheckNetworkConnection()
    lateinit var handler: Handler
    lateinit var updateManager: ReviewManager
    val db = CreateDB.db
    var firstUpload = 0
    var sendRequest = true
    var checkDatabase = true

    companion object {
        var zodiacList = ArrayList<Zodiac>()
        var percentageList = ArrayList<Percentages>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreference.init(this)
        percentageList = getPercentagesList()
        navigationUI()
        changeTextLang()
        handler = Handler(Looper.getMainLooper())
        zodiacList = loadHoroscopeWithLang(SharedPreference.lang!!)

//        if (SharedPreference.updateDB == 0) {
//            checkNetworkConnection()
//        }
//        if (SharedPreference.updateDBTime == "") {
//            SharedPreference.updateDBTime = getTime()
//            handler = Handler(Looper.getMainLooper())
//            firstUpload = 1
//            handler.postDelayed(runnable, 0)
//        }
    }

    private fun checkNetworkConnection() {
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(checkNetworkConnection, intentFilter)
        handler.postDelayed(runnable, 0)
    }

    private var runnable = object : Runnable {
        override fun run() {
            if (NetworkHelper(this@MainActivity).isNetworkConnected()) {
                if (firstUpload == 1) {
                    if (db.zodiacBaseDao().getAll().isEmpty() && sendRequest) {
                        UploadData.uploadHoroscope(1, 2)
                        sendRequest = false
                    }
                    if (db.zodiacBaseDao().getAll().isNotEmpty()) {
                        handler.removeCallbacks(this)
                        uploadWorkManager()
                        checkDatabase = false
                    }
                } else {
                    UpdateData.updateHoroscope(1)
                    handler.removeCallbacks(this)

                    checkDatabase = false
                }
            }
            if (checkDatabase) {
                handler.postDelayed(this, 350)
            }
        }
    }

    private fun uploadWorkManager() {
        val uploadChineseHoroscope: WorkRequest =
            PeriodicWorkRequestBuilder<UpdateDBService>(2, TimeUnit.HOURS)
                .build()
        WorkManager
            .getInstance(this)
            .enqueue(uploadChineseHoroscope)
    }

    private fun getTime(): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val date = LocalDateTime.now()
        return date.format(formatter)
    }

    private fun navigationUI() {
        navController = findNavController(R.id.nav_host_fragment_content_main)
        val btnView = binding.btnNav
        btnView.itemIconTintList = null
        btnView.elevation = 0F
        btnView.itemIconTintList = null
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home,
            R.id.nav_book,
            R.id.nav_compatibility,
            R.id.nav_settings,
        ))
        btnView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            btnView.isVisible = destination.id !in destinationsWithoutBottomNav
        }

    }

    private val destinationsWithoutBottomNav = listOf(R.id.predictionsFragment,
        R.id.aboutZodiacFragment,
        R.id.resultCompatibilityFragment)

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
        val json: String? = try {
            val inputStream: InputStream = assets.open("$jsonName.json")
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun changeTextLang() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.btn_nav)
        bottomNav.menu.findItem(R.id.nav_home)
        bottomNav.menu.findItem(R.id.nav_book)
        bottomNav.menu.findItem(R.id.nav_compatibility)
        bottomNav.menu.findItem(R.id.nav_settings)
    }

    private fun getPercentagesList(): ArrayList<Percentages> {
        var list = ArrayList<Percentages>()
        for (i in 0 until 12) {
            val percentages = Percentages(getRandomNumber(),
                getRandomNumber(),
                getRandomNumber(),
                getRandomNumber(),
                getRandomNumber(),
                getRandomNumber())
            list.add(percentages)
        }
        return list
    }

    private fun getRandomNumber(): Int {
        return Random.nextInt(65..85)
    }

    override fun sendInput(input: String?) {
        changeTextLang()
        navController.navigate(R.id.nav_settings)
        zodiacList = loadHoroscopeWithLang(SharedPreference.lang!!)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(checkNetworkConnection)
    }

}