package com.kemalcodes.composetutorial.api

// Tutorial #12: Retrofit — Client singleton
// Creates one Retrofit instance for the entire app
// ignoreUnknownKeys = true prevents crashes when the API returns extra fields

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitClient {

    private val json = Json {
        ignoreUnknownKeys = true  // Don't crash on extra JSON fields
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val userApi: UserApi = retrofit.create(UserApi::class.java)
}
