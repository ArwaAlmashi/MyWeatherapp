package com.example.myweatherapp.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.databinding.RowBinding

class RVAdapter(private var weatherDataItems: ArrayList<WeatherData>) :
    RecyclerView.Adapter<RVAdapter.RVHolder>() {

    // Holder
    class RVHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root)

    // Adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVHolder {
        return RVHolder(
            RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RVHolder, position: Int) {
        val item = weatherDataItems[position]
        holder.binding.apply {
            weatherTitle.text = item.title
            weatherAmount.text = item.amount
            weatherImage.setImageResource(item.image)
        }
    }

    override fun getItemCount(): Int = weatherDataItems.size

    // Set
    @SuppressLint("NotifyDataSetChanged")

    fun setToWeatherItem(weatherDataItems: java.util.ArrayList<WeatherData>) {
        this.weatherDataItems = weatherDataItems
        notifyDataSetChanged()
    }

}