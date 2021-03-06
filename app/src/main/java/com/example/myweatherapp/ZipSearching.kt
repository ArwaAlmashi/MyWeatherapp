package com.example.myweatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myweatherapp.databinding.ActivityZipSearchingBinding

class ZipSearching : AppCompatActivity() {

    private lateinit var binding: ActivityZipSearchingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZipSearchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set UI
        supportActionBar?.hide()

        binding.searchButton.setOnClickListener {
            val userInput = binding.zipUserInput
            zipCode = userInput.text.toString()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }
}