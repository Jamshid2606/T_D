package com.talabalardaftari.tdcleanhilt.domain.repository

import androidx.lifecycle.LiveData
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.main.model.request.AddNoteBookRequest
import com.talabalardaftari.tdcleanhilt.data.main.model.response.AddNoteBookResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.SaveAttachmentResponse
import okhttp3.MultipartBody
import java.net.URI

interface MainRepository {
    fun saveAttachment(imageBody: MultipartBody.Part) : LiveData<BaseNetworkResult<SaveAttachmentResponse>>
    fun addNoteBook(addNoteBookRequest: AddNoteBookRequest) : LiveData<BaseNetworkResult<AddNoteBookResponse>>
}