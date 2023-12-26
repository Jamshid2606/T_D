package com.talabalardaftari.tdcleanhilt.data.auth.model.request

data class RegisterUserRequest(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val phoneNumber: String,
    val username: String
)