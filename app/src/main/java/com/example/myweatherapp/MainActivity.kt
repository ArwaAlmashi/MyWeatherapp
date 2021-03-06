package com.example.myweatherapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.databinding.ActivityMainBinding
import com.example.myweatherapp.model.WeatherModel
import com.example.myweatherapp.recyclerview.RVAdapter
import com.example.myweatherapp.recyclerview.WeatherData
import com.example.myweatherapp.services.APIInterface
import com.example.myweatherapp.errorActivity
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

const val apiId = "77a8635cea42b2086348c958c171f78a"
var zipCode = "47860"


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RVAdapter

    private var weatherDataItems = arrayListOf(
        WeatherData("Sunrise", "", R.drawable.sunrise),
        WeatherData("Sunset", "", R.drawable.sunset),
        WeatherData("Humidity", "", R.drawable.humidity),
        WeatherData("Wind", "", R.drawable.tornado),
        WeatherData("Pressure", "", R.drawable.barometer)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set UI
        supportActionBar?.hide()
        setRecyclerView()

        // API
        getAllData()

        // Refresh
        binding.refreshButton.setOnClickListener {
            val intent = Intent(this, ZipSearching::class.java)
            startActivity(intent)
        }


    }

    // Recyclerview Setting
    private fun setRecyclerView() {
        recyclerView = binding.recyclerview
        adapter = RVAdapter(weatherDataItems)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

    }

    // Convert Date
    @SuppressLint("SimpleDateFormat")
    private fun convertIntToTime(time: Int): String {
        val date = Date(time.toLong())
        val format = SimpleDateFormat("HH:mm")
        return format.format(date)
    }

    // Current Date
    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        return "Last updated: ${sdf.format(Date())}"
    }

    // API Request
    private fun getAllData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
            .create(APIInterface::class.java)

        val retrofitData = retrofitBuilder.getData("weather?zip=$zipCode&units=metric&appid=$apiId")
        retrofitData.enqueue(object : Callback<WeatherModel> {
            @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                try {
                    val weatherModel = response.body()!!
                    val weather = weatherModel.weather[0]
                    val weatherDescription = weather.description

                    Log.d("MainActivity", "${weatherModel.sys.sunrise}")
                    runOnUiThread {

                        // Textview
                        binding.cityName.text = "${weatherModel.name}, ${weatherModel.sys.country}"
                        binding.weatherDescription.text = weatherModel.weather[0].description
                        binding.temperatureAmount.text = "${weatherModel.main.temp.toInt()}??C"
                        binding.lowTemperature.text = "Low: ${weatherModel.main.temp_min.toInt()}??C"
                        binding.highTemperature.text =
                            "High: ${weatherModel.main.temp_max.toInt()}??C"

                        // Recyclerview items
                        weatherDataItems[0].amount =
                            "${convertIntToTime(weatherModel.sys.sunrise)} AM"
                        weatherDataItems[1].amount =
                            "${convertIntToTime(weatherModel.sys.sunset)} PM"
                        weatherDataItems[2].amount = "${weatherModel.main.humidity}"
                        weatherDataItems[3].amount = "${weatherModel.wind.speed}"
                        weatherDataItems[4].amount = "${weatherModel.main.pressure}"

                        recyclerView.adapter?.notifyDataSetChanged()

                        binding.lastUpdated.text = getCurrentDate()


                    }

                } catch (ex: Exception) {
                    Log.d("MainActivity", "Error: ${ex.message}")
                    val intent = Intent(this@MainActivity, errorActivity::class.java)
                    startActivity(intent)

                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.d("MainActivity", "${t.message}")
            }
        })
    }


}
