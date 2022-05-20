package com.craftie.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProvincesCount(
    val leinster: Leinster,
    val munster: Munster,
    val ulster: Ulster,
    val connaught: Connaught,
)

@Serializable
data class Leinster(
    val name: String,
    val amount: Int,
    val imageUrl: String,
)

@Serializable
data class Munster(
    val name: String,
    val amount: Int,
    val imageUrl: String,
)

@Serializable
data class Ulster(
    val name: String,
    val amount: Int,
    val imageUrl: String,
)

@Serializable
data class Connaught(
    val name: String,
    val amount: Int,
    val imageUrl: String,
)