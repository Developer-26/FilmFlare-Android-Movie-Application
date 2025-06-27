package com.filmflare

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.jsonbin.io/") // Base URL of the API
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}