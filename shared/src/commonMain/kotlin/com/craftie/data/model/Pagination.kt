package com.craftie.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pagination<T>(
    val info: Info,
    @SerialName("results")
    val results: List<T>
)

@Serializable
data class Info(
    val count: Int,
    val next: Int? = null,
    val previous: Int? = null
)