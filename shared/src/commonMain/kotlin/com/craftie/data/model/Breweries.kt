package com.craftie.data.model

data class Brewery(
    val _id: String? = null,
    val name: String,
    val description: String,
    val location: Location,
    val imageUrl: String,
    val brandImageUrl: String
)