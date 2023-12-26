package com.talabalardaftari.tdcleanhilt.domain.repository

import androidx.lifecycle.LiveData
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.LoginRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.RegisterUserRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.VerifyEmailRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.response.LoginResponse
import com.talabalardaftari.tdcleanhilt.data.auth.model.response.RegisterUserResponse
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult

interface AuthRepository {
    fun sendEmail(email:String) : LiveData<BaseNetworkResult<String>>
    fun verifyEmail(verifyEmailRequest: VerifyEmailRequest) : LiveData<BaseNetworkResult<Boolean>>
    fun registration(registerUserRequest: RegisterUserRequest):LiveData<BaseNetworkResult<RegisterUserResponse>>
    fun login(loginRequest: LoginRequest):LiveData<BaseNetworkResult<LoginResponse>>
    fun usernameExists(username:String):LiveData<BaseNetworkResult<Boolean>>
}