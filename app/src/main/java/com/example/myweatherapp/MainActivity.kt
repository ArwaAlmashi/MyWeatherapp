package com.example.myweatherapp

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

const val apiId= "77a8635cea42b2086348c958c171f78a"
const val zipCode = "47860"

class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RVAdapter

    private var weatherDataItems = arrayListOf<WeatherData>(
        WeatherData("Sunrise", "12:00 pm", R.drawable.sunrise),
        WeatherData("Sunset", "12:00 pm", R.drawable.sunset),
        WeatherData("Humidity", "12:00 pm", R.drawable.humidity),
        WeatherData("Wind", "12:00 pm", R.drawable.tornado),
        WeatherData("Pressure", "12:00 pm", R.drawable.barometer),
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


    }

    private fun setRecyclerView() {
        recyclerView = binding.recyclerview
        adapter = RVAdapter(weatherDataItems)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

    }

//    https://api.openweathermap.org/data/2.5/weather?zip=47860,us&units=metric&appid=77a8635cea42b2086348c958c171f78a
    private fun getAllData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build()
            .create(APIInterface::class.java)

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                try {
                    val weatherModel = response.body()!!
                    val weather = weatherModel.weather[0]
                    val weatherDescription = weather.description


                    runOnUiThread {
                    }

                } catch (ex: Exception) {
                    Log.d("MainActivity", "Error: ${ex.message}")
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Log.d("MainActivity", "${t.message}")
            }
        })
    }





}



































//    private fun requestAPI() {
//        CoroutineScope(IO).launch {
//            val data = async{ fetchData() }.await()
//            if (data.isNotEmpty()) {
//                populateRV(data)
//            } else {
//                Toast.makeText(this@MainActivity, "Noy found data", Toast.LENGTH_LONG)
//            }
//        }
//    }
//
//    private fun fetchData(): String {
//        var response = ""
//        try {
//            response = URL("https://api.openweathermap.org/data/2.5/weather?zip=47860,us&units=metric&appid=77a8635cea42b2086348c958c171f78a").readText()
//        } catch (ex: Exception) {
//            Toast.makeText(this, "Error: ${ex.message}", Toast.LENGTH_LONG).show()
//        }
//        return response
//    }
//
//    private suspend fun populateRV(result: String) {
//        val jsonObjects = JSONObject(result)
//        //val weather = jsonObjects.getJSONArray("weather")
//        val weather = jsonObjects.getJSONArray("weather")[0]
//
//
//        Log.d("MainActivity", "${weather}")
//
//        runOnUiThread {
//
//        }
//    }