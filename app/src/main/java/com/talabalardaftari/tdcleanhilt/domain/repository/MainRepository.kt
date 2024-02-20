package com.talabalardaftari.tdcleanhilt.domain.repository

import androidx.lifecycle.LiveData
import com.talabalardaftari.tdcleanhilt.data.auth.model.response.getNoteBooksResponse.GetNoteBooksResponse
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.main.model.request.AddNoteBookRequest
import com.talabalardaftari.tdcleanhilt.data.main.model.response.AddNoteBookResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.GetNoteBooksResponseItem
import com.talabalardaftari.tdcleanhilt.data.main.model.response.SaveAttachmentResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.StudentDTO
import okhttp3.MultipartBody

interface MainRepository {
    fun saveAttachment(file: MultipartBody.Part) : LiveData<BaseNetworkResult<SaveAttachmentResponse>>
    fun addNoteBook(addNoteBookRequest: AddNoteBookRequest) : LiveData<BaseNetworkResult<AddNoteBookResponse>>
    fun getNoteBooks(page:Int, size:Int, sortBy:String, sortDirection:String, search:String) : LiveData<BaseNetworkResult<List<GetNoteBooksResponseItem>>>
    fun me():LiveData<BaseNetworkResult<StudentDTO>>
    fun updateProfile(studentDTO: StudentDTO):LiveData<BaseNetworkResult<StudentDTO>>
}