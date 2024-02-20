package com.talabalardaftari.tdcleanhilt.data.main.model.response

data class GetNoteBooksResponseItem(
    val attachmentId: String,
    val id: String,
    val name: String,
    val studentDto: Any
)