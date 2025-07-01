package com.example.theweatherapp.data

data class WeatherResponse(
    val name: String,
    val main: Main,
    val description: Des
)

data class Main(
    val temp: Float,
    val humidity: Int
)

data class Des(
    val description: String = "Null"
)