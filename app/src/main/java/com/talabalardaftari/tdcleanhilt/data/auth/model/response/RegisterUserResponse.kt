package com.talabalardaftari.tdcleanhilt.data.auth.model.response

data class RegisterUserResponse(
    val birthDate: Any,
    val educationInfo: Any,
    val email: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val phoneNumber: String,
    val studyType: Any,
    val username: String
)