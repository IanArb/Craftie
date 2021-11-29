package com.craftie.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Beer(
    @SerialName("_id")
    val id: String,
    val name: String,
    val description: String,
    val tastingNotes: String,
    val imageUrl: String,
    val type: String,
    val style: String,
    val abv: Amount,
    val ibu: Amount? = null,
    val breweryInfo: BreweryInfo,
    val isFeatured: Boolean
)

@Serializable
data class Amount(
    val value: Float,
    val unit: String
)

@Serializable
data class BreweryInfo(
    val name: String,
    val description: String,
    val brandImageUrl: String? = null,
    val location: Location,
)

@Serializable
data class Location(
    val county: String,
    val province: String,
    val address: String,
    val latLng: LatLng
)

@Serializable
data class LatLng(
    val latitude: Double,
    val longitude: Double
)