package com.exemple.myweatherapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.exemple.myweatherapp.databinding.FragmentWeatherBinding
import com.exemple.myweatherapp.ui.viewmodel.WeatherViewModel
import com.exemple.myweatherapp.util.Account
import kotlin.math.roundToInt

class WeatherFragment : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var weatherViewModel: WeatherViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        weatherViewModel =
            ViewModelProvider(this.requireActivity()).get(WeatherViewModel::class.java)
        weatherViewModel.fillListImage()

        val city = arguments?.get("city").toString()
        (binding.textSearch.text)?.append(city)
        weatherViewModel.getData(this.requireContext(), city)
        weatherViewModel.weatherData.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                response.body().let {
                    if (it != null) {

                        binding.city.text = it.name
                        binding.country.text = it.sys.country

                        val descriptionShort = it.weather[0].main
                        binding.descriptionShort.text = descriptionShort
                        weatherViewModel.getImage(descriptionShort)?.let { it1 ->
                            binding.icon.setImageResource(
                                it1
                            )
                        }

                        binding.description.text = it.weather[0].description
                        val temp = it.main.temp.roundToInt()
                        binding.temp.text = "$temp °C "
                        binding.tempF.text = "${CelsiusToFahrenheit(temp)} °F "

                        Account.city = (binding.textSearch.text).toString()
                        Account.saveData(this.requireContext())
                    }
                }
            }
        })

        binding.startSearch.setOnClickListener {
            weatherViewModel.getData(this.requireContext(), (binding.textSearch.text).toString())
        }

        return binding.root
    }

    fun CelsiusToFahrenheit(valueC: Int): Int {
        var tempF = valueC - (valueC * 0.1).toInt()
        tempF = tempF * 2 + 32
        return tempF
    }
}