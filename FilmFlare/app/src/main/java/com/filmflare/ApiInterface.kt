package com.filmflare

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    // The base URL is already set in the RetroInstance
    @GET("/v3/b/66f804f7e41b4d34e4397fbc")
    fun getData(): Call<responseDataClass>
}