package com.talabalardaftari.tdcleanhilt.data.main

import com.talabalardaftari.tdcleanhilt.data.main.model.request.AddNoteBookRequest
import com.talabalardaftari.tdcleanhilt.data.main.model.response.AddNoteBookResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.SaveAttachmentResponse
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MainService {
    @Multipart
    @POST("/attachment/save")
    fun saveAttachment(@Part image:MultipartBody.Part) : Observable<SaveAttachmentResponse>

    @POST("/notebook")
    fun addNoteBook(@Body addNoteBookRequest: AddNoteBookRequest) : Observable<AddNoteBookResponse>
}