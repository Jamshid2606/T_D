package com.talabalardaftari.tdcleanhilt.data.main.model.response

data class AddNoteBookResponse(
    val attachmentId: String,
    val id: String,
    val name: String,
    val studentDto: Any
)