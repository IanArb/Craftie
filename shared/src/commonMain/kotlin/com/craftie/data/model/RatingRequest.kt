package com.craftie.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingRequest(
    @SerialName("beerId")
    val beerId: String = "",
    @SerialName("authorName")
    val authorName: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("rating")
    val rating: Double
)

@Serializable
data class RatingResponse(
    @SerialName("_id")
    val id: String,
    val beerId: String,
    val authorName: String? = null,
    val description: String? = null,
    val rating: Double,
    val date: String
)
@Serializable
data class RatingResult(
    @SerialName("averageRating")
    val averageRating: Double,
    @SerialName("totalReviews")
    val totalReviews: Int
)