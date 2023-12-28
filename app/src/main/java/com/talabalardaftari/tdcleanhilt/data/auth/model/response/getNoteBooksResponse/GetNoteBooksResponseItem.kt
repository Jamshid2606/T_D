package com.talabalardaftari.tdcleanhilt.data.auth.model.response.getNoteBooksResponse

data class GetNoteBooksResponseItem(
    val attachmentId: String,
    val id: String,
    val name: String,
    val studentDto: Any
)