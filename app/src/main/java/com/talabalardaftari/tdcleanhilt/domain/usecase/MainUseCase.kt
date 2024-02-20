package com.talabalardaftari.tdcleanhilt.domain.usecase

import androidx.lifecycle.LiveData
import com.talabalardaftari.tdcleanhilt.data.auth.model.response.getNoteBooksResponse.GetNoteBooksResponse
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.main.model.request.AddNoteBookRequest
import com.talabalardaftari.tdcleanhilt.data.main.model.response.AddNoteBookResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.GetNoteBooksResponseItem
import com.talabalardaftari.tdcleanhilt.data.main.model.response.SaveAttachmentResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.StudentDTO
import com.talabalardaftari.tdcleanhilt.domain.repository.MainRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class MainUseCase @Inject constructor(private val mainRepository: MainRepository) {
    fun saveAttachment(imageBody: MultipartBody.Part):LiveData<BaseNetworkResult<SaveAttachmentResponse>>{
        return mainRepository.saveAttachment(imageBody)
    }
    fun addNoteBook(addNoteBookRequest: AddNoteBookRequest):LiveData<BaseNetworkResult<AddNoteBookResponse>>{
        return mainRepository.addNoteBook(addNoteBookRequest)
    }
    fun getNoteBooks(page:Int, size:Int, sortBy:String, sortDirection:String, search:String) : LiveData<BaseNetworkResult<List<GetNoteBooksResponseItem>>>{
        return mainRepository.getNoteBooks(page, size, sortBy, sortDirection, search)
    }
    fun me():LiveData<BaseNetworkResult<StudentDTO>>{
        return mainRepository.me()
    }
    fun updateProfile(studentDTO: StudentDTO):LiveData<BaseNetworkResult<StudentDTO>>{
        return mainRepository.updateProfile(studentDTO)
    }
}