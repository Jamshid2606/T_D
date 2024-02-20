package com.talabalardaftari.tdcleanhilt.data.main

import com.talabalardaftari.tdcleanhilt.data.main.model.request.AddNoteBookRequest
import com.talabalardaftari.tdcleanhilt.data.main.model.response.AddNoteBookResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.GetNoteBooksResponseItem
import com.talabalardaftari.tdcleanhilt.data.main.model.response.SaveAttachmentResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.StudentDTO
import io.reactivex.rxjava3.core.Observable
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface MainService {
    @Multipart
    @POST("/attachment/save")
    fun saveAttachment(@Part file:MultipartBody.Part) : Observable<SaveAttachmentResponse>

    @POST("/notebook")
    fun addNoteBook(@Body addNoteBookRequest: AddNoteBookRequest) : Observable<AddNoteBookResponse>

    @GET("/notebook")
    fun getNoteBooks(
        @Query("page") page:Int, @Query("size") size:Int, @Query("sortBy") sortBy:String,@Query("sortDirection") sortDirection:String, @Query("search") search:String
    ):Observable<List<GetNoteBooksResponseItem>>

    @GET("/auth/me")
    fun me():Observable<StudentDTO>

    @PATCH("/student/update")
    fun updateProfile(@Body studentDTO: StudentDTO) : Observable<StudentDTO>
}