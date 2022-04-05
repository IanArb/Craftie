package com.craftie.data.model

data class JwtToken(val token: String)

data class Login(val username: String, val password: String)