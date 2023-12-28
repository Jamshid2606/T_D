package com.talabalardaftari.tdcleanhilt.data.main.model.response

data class SaveAttachmentResponse(
    val contentType: String,
    val createdAt: String,
    val createdBy: Any,
    val deleted: Boolean,
    val id: String,
    val name: String,
    val originalName: String,
    val path: String,
    val size: Int,
    val status: Int,
    val updatedAt: String
)