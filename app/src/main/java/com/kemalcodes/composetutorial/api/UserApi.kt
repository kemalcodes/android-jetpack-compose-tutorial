package com.kemalcodes.composetutorial.api

// Tutorial #12: Retrofit — API interface
// Each function = one HTTP request
// suspend = runs on background thread, doesn't block UI
// Return type = Retrofit parses JSON automatically

import retrofit2.http.GET

interface UserApi {
    // GET https://jsonplaceholder.typicode.com/users
    @GET("users")
    suspend fun getUsers(): List<User>
}
