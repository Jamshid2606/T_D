package com.talabalardaftari.tdcleanhilt.data.auth.model.request

data class VerifyEmailRequest(
    val code: String,
    val email: String
)