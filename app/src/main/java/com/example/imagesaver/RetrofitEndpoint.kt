package com.example.imagesaver

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitEndpoint {

    // URL : "https://jsonplaceholder.typicode.com/photos"
    @GET("${BASE_URL}photos")
   fun getData(): Call<List<ApiResponse>>

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create(): RetrofitEndpoint {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RetrofitEndpoint::class.java)
        }
    }
}
