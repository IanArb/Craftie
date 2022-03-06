package com.craftie.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Brewery(
    @SerialName("_id")
    val id: String? = null,
    val name: String,
    val description: String,
    val location: Location,
    val imageUrl: String,
    val brandImageUrl: String
)

@Serializable
data class Result(
    @SerialName("_id")
    val id: String,
    val name: String,
    val description: String,
    val location: Location,
    val imageUrl: String,
    val brandImageUrl: String
)