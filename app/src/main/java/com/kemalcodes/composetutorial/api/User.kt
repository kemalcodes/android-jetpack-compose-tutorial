package com.kemalcodes.composetutorial.api

// Tutorial #12: Retrofit — Data model
// @Serializable tells Kotlin Serialization how to parse JSON into this class

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String
)
