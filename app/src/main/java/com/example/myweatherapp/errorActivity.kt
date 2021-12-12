package com.example.myweatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myweatherapp.MainActivity
import com.example.myweatherapp.databinding.ActivityErrorBinding
import com.example.myweatherapp.zipCode

private lateinit var binding: ActivityErrorBinding

class errorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set UI
        supportActionBar?.hide()

        binding.retryButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}