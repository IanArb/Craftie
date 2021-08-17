package com.craftie.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Province(
    @SerialName("_id")
    val id: String? = null,
    val name: String,
    val imageUrl: String
)