package com.talabalardaftari.tdcleanhilt.data.auth

import com.talabalardaftari.tdcleanhilt.data.auth.model.request.LoginRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.RegisterUserRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.VerifyEmailRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.response.LoginResponse
import com.talabalardaftari.tdcleanhilt.data.auth.model.response.RegisterUserResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @GET("/auth/send-email")
    fun sendEmail(@Query("email") email: String): Call<String>

    @POST("/auth/verify-email")
    fun verifyEmail(@Body verifyEmailRequest: VerifyEmailRequest): Call<Boolean>
    @POST("/auth/registration")
    fun registration(@Body registerUserRequest: RegisterUserRequest): Observable<RegisterUserResponse>
    @POST("/auth/login")
    fun login(@Body loginRequest: LoginRequest): Observable<LoginResponse>
//    @GET("/student/username_exists?username={username}")
//    fun usernameExists(@Path("username") username:String): Flow<Boolean>
}