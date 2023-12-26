package com.talabalardaftari.tdcleanhilt.presentation.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.LoginRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.RegisterUserRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.VerifyEmailRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.response.LoginResponse
import com.talabalardaftari.tdcleanhilt.data.auth.model.response.RegisterUserResponse
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) :ViewModel() {
    private val _sendEmail = MutableLiveData<BaseNetworkResult<String>>()
    val sendEmail : LiveData<BaseNetworkResult<String>>
        get() = _sendEmail
    private val _verifyEmail = MutableLiveData<BaseNetworkResult<Boolean>>()
    val verifyEmail : LiveData<BaseNetworkResult<Boolean>>
        get() = _verifyEmail
    private val _registration = MutableLiveData<BaseNetworkResult<RegisterUserResponse>>()
    val registration:LiveData<BaseNetworkResult<RegisterUserResponse>>
        get() = _registration
    private val _login = MutableLiveData<BaseNetworkResult<LoginResponse>>()
    val login:LiveData<BaseNetworkResult<LoginResponse>>
        get() = _login

    fun sendEmail(email:String){
        _sendEmail.value = BaseNetworkResult.Loading(true)
        authUseCase.sendEmail(email).observeForever {
            when(it){
                is BaseNetworkResult.Error -> {
                    _sendEmail.value = BaseNetworkResult.Error(it.message)
                    Log.d("AuthViewModel, sendEmailError:  ", it.message.toString())
                }
                is BaseNetworkResult.Loading -> {
                    _sendEmail.value = BaseNetworkResult.Loading(it.isLoading)
                }
                is BaseNetworkResult.Success ->{
                    if (it.data!= null){
                        _sendEmail.value = BaseNetworkResult.Success(it.data)
                    }else{
                        _sendEmail.value = BaseNetworkResult.Error("Response data is null")
                        Log.d("AuthViewModel, sendEmailError data is NUll:  ", it.message.toString())
                    }
                }
            }
        }
    }
    fun verifyEmail(verifyEmailRequest: VerifyEmailRequest){
        _verifyEmail.value = BaseNetworkResult.Loading(true)
        authUseCase.verifyEmail(verifyEmailRequest).observeForever {
            when(it){
                is BaseNetworkResult.Error -> {
                    _verifyEmail.value = BaseNetworkResult.Error(it.message)
                }
                is BaseNetworkResult.Loading -> {
                    _verifyEmail.value = BaseNetworkResult.Loading(it.isLoading)
                }
                is BaseNetworkResult.Success -> {
                    if (it.data!=null){
                        _verifyEmail.value = BaseNetworkResult.Success(it.data)
                    }else{
                        _verifyEmail.value = BaseNetworkResult.Error("Response data is null")
                        Log.d("AuthViewModel, verifyEmail data is null", it.message.toString())
                    }
                }
            }
        }
    }
    fun registration(registerUserRequest: RegisterUserRequest){
        _registration.value = BaseNetworkResult.Loading(true)
        authUseCase.registration(registerUserRequest).observeForever {
            when(it){
                is BaseNetworkResult.Error -> {
                    _registration.value = BaseNetworkResult.Error(it.message)
                }
                is BaseNetworkResult.Loading -> {
                    _registration.value = BaseNetworkResult.Loading(it.isLoading)
                }

                is BaseNetworkResult.Success -> {
                    if (it.data!=null){
                        _registration.value = BaseNetworkResult.Success(it.data)
                    }else{
                        _registration.value = BaseNetworkResult.Error("Response data is null")
                        Log.d("AuthViewModel, registration data is null", it.message.toString())
                    }
                }
            }
        }
    }
    fun login(loginRequest: LoginRequest){
        _login.value = BaseNetworkResult.Loading(true)
        authUseCase.login(loginRequest).observeForever {
            when(it){
                is BaseNetworkResult.Error -> {
                    _login.value = BaseNetworkResult.Error(it.message)
                }
                is BaseNetworkResult.Loading -> {
                    _login.value = BaseNetworkResult.Loading(it.isLoading)
                }
                is BaseNetworkResult.Success -> {
                    if (it.data!=null){
                        _login.value = BaseNetworkResult.Success(it.data)
                    }else{
                        _login.value = BaseNetworkResult.Error("Response data is null")
                        Log.d("AuthViewModel, login data is null", it.message.toString())
                    }
                }
            }
        }
    }
}