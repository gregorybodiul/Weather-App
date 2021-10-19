package com.exemple.myweatherapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exemple.myweatherapp.R
import com.exemple.myweatherapp.api.RetrofitInstance
import com.exemple.myweatherapp.api.model.Data
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class WeatherViewModel : ViewModel() {
    var weatherData: MutableLiveData<Response<Data>> = MutableLiveData()
    private val host = "community-open-weather-map.p.rapidapi.com"
    private val key = "8ba2c7dff7mshe39c9c2f19cbb2ap17f558jsnd97b35035a40"
    private val listImage = HashMap<String, Int>()

    fun getData(context: Context, city: String) {
        viewModelScope.launch {
            try {
                val response: Response<Data> =
                    RetrofitInstance.api.getWeather(host, key, city, "ru", "metric")
                weatherData.value = response
            } catch (e: Exception) {
                println(e.message)
                delay(2000)
                getData(context, city)
            }
        }
    }

    fun getImage(description: String): Int? {
        return listImage[description]
    }

    fun fillListImage() {
        listImage["Clouds"] = R.drawable.clouds
        listImage["Rain"] = R.drawable.rain
        listImage["Clear"] = R.drawable.clear
        listImage["Drizzle"] = R.drawable.drizzle
    }

}
