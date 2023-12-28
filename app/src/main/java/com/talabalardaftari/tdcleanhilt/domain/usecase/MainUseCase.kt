package com.talabalardaftari.tdcleanhilt.domain.usecase

import androidx.lifecycle.LiveData
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.main.model.request.AddNoteBookRequest
import com.talabalardaftari.tdcleanhilt.data.main.model.response.AddNoteBookResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.SaveAttachmentResponse
import com.talabalardaftari.tdcleanhilt.domain.repository.MainRepository
import okhttp3.MultipartBody
import java.net.URI
import javax.inject.Inject

class MainUseCase @Inject constructor(private val mainRepository: MainRepository) {
    fun saveAttachment(imageBody: MultipartBody.Part):LiveData<BaseNetworkResult<SaveAttachmentResponse>>{
        return mainRepository.saveAttachment(imageBody)
    }
    fun addNoteBook(addNoteBookRequest: AddNoteBookRequest):LiveData<BaseNetworkResult<AddNoteBookResponse>>{
        return mainRepository.addNoteBook(addNoteBookRequest)
    }
}