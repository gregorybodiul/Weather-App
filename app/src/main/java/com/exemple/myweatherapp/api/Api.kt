package com.exemple.myweatherapp.api

import com.exemple.myweatherapp.api.model.Data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface Api {
    @GET("/weather")
    suspend fun getWeather(
        @Header("x-rapidapi-host") xRapidapiHost: String,
        @Header("x-rapidapi-key") xRapidapiKey: String,
        @Query("q") city: String,            //City
        //@Query("lat") lat: Int,              //Must be used with lon. Get current weather data when you know the latitude of the city.
        //@Query("lon") lon: Int,              //Must be used with lat. Get current weather data when you know the longitude of the city.
        //@Query("callback") callback: String,//To use JavaScrip code you can transfer callback functionName to JSONP callback. (test)
        //@Query("id") idCity: Int,           //Get current weather data when you know the city ID. Not to be used with lon, lat, or q
        @Query("lang") language: String,    //English - en, Russian - ru, Italian - it, Spanish - sp, Ukrainian - ua, German - de, Portuguese - pt, Romanian - ro, Polish - pl, Finnish - fi, Dutch - nl, French - fr, Bulgarian - bg, Swedish - se, Chinese Traditional - zhtw, Chinese Simplified - zhcn, Turkish - tr
        @Query("units") units: String,      //metric or imperial
        //@Query("mode") mode: String?,        //If left blank will default to JSON output. Ability to retrieve data in XML or HTML.
    ): Response<Data>
}