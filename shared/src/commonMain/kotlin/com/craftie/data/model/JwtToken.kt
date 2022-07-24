package com.craftie.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JwtToken(
    @SerialName("token")
    val token: String
)

@Serializable
data class Login(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String
)