package com.craftie.data.model

import kotlinx.serialization.Serializable

@Serializable
data class JwtToken(val token: String)

@Serializable
data class Login(val username: String, val password: String)