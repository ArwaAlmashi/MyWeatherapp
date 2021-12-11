package com.example.myweatherapp.services

import com.example.myweatherapp.apiId
import com.example.myweatherapp.model.WeatherModel
import com.example.myweatherapp.zipCode
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url




interface APIInterface {
    @GET
    fun getData(@Url url: String): Call<WeatherModel>
}