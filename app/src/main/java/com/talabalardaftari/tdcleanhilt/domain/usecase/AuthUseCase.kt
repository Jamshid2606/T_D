package com.talabalardaftari.tdcleanhilt.domain.usecase

import androidx.lifecycle.LiveData
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.LoginRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.RegisterUserRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.VerifyEmailRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.response.LoginResponse
import com.talabalardaftari.tdcleanhilt.data.auth.model.response.RegisterUserResponse
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val authRepository: AuthRepository) {
    fun sendEmail(email:String) : LiveData<BaseNetworkResult<String>>{
        return authRepository.sendEmail(email)
    }
    fun verifyEmail(verifyEmailRequest: VerifyEmailRequest) : LiveData<BaseNetworkResult<Boolean>>{
        return authRepository.verifyEmail(verifyEmailRequest)
    }
    fun registration(registerUserRequest: RegisterUserRequest) : LiveData<BaseNetworkResult<RegisterUserResponse>>{
        return authRepository.registration(registerUserRequest)
    }
    fun login(loginRequest: LoginRequest) : LiveData<BaseNetworkResult<LoginResponse>>{
        return authRepository.login(loginRequest)
    }
    fun usernameExists(username:String) : LiveData<BaseNetworkResult<Boolean>>{
        return authRepository.usernameExists(username)
    }
}
