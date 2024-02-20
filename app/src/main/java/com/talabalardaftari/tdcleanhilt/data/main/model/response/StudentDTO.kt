package com.talabalardaftari.tdcleanhilt.data.main.model.response

data class StudentDTO(
    val birthDate: String,
    val educationInfo: EducationInfo,
    val email: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val phoneNumber: String,
    val studyType: String,
    val username: String
)