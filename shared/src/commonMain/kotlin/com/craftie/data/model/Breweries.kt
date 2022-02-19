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
data class BreweryPagination(
    val info: Info,
    val results: List<Result>
)

@Serializable
data class Info(
    val count: Int,
    val next: Int? = null,
    val previous: Int? = null
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