package com.example.myweatherapp.services

import com.example.myweatherapp.apiId
import com.example.myweatherapp.model.WeatherModel
import com.example.myweatherapp.zipCode
import retrofit2.Call
import retrofit2.http.GET


interface APIInterface {

    @GET("weather?zip=$zipCode,us&units=metric&appid=$apiId")
    fun getData(): Call<WeatherModel>
}