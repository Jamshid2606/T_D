package com.talabalardaftari.tdcleanhilt.data.auth.model.request

data class LoginRequest(
    val password: String,
    val username: String
)