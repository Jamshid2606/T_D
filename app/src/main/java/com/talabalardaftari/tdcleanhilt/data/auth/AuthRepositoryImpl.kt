package com.talabalardaftari.tdcleanhilt.data.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.LoginRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.RegisterUserRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.VerifyEmailRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.response.LoginResponse
import com.talabalardaftari.tdcleanhilt.data.auth.model.response.RegisterUserResponse
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.domain.repository.AuthRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authService:AuthService) : AuthRepository {
    override fun sendEmail(email: String): LiveData<BaseNetworkResult<String>> {
        val responsse = MutableLiveData<BaseNetworkResult<String>>()
        responsse.value = BaseNetworkResult.Loading(true)
        authService.sendEmail(email).enqueue(object :Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.code()==200){
                    if (response.body() != null){
                        responsse.value = BaseNetworkResult.Success(response.body()?:"")
                    }else{
                        responsse.value = BaseNetworkResult.Error(response.errorBody().toString())
                    }
                }else{
                    responsse.value = BaseNetworkResult.Error(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                responsse.value = BaseNetworkResult.Error(t.message)
            }
        })
        return responsse
    }

    override fun verifyEmail(verifyEmailRequest: VerifyEmailRequest): LiveData<BaseNetworkResult<Boolean>> {
        val responsse = MutableLiveData<BaseNetworkResult<Boolean>>()
        responsse.value = BaseNetworkResult.Loading(true)
        authService.verifyEmail(verifyEmailRequest).enqueue(object :Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() == 200){
                    if (response.body() == true){
                        responsse.value = BaseNetworkResult.Success(true)
                    }else{
                        responsse.value = BaseNetworkResult.Error("Kiritilgan parol xato")
                    }
                }else{
                    responsse.value = BaseNetworkResult.Error("Xatolik: response code:${response.code()}, ||| ${response.errorBody()} ")
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                responsse.value = BaseNetworkResult.Error("Xatolik: ||| ${t.message} ")
            }
        })
        return responsse
    }

    override fun registration(registerUserRequest: RegisterUserRequest): LiveData<BaseNetworkResult<RegisterUserResponse>> {
        val response = MutableLiveData<BaseNetworkResult<RegisterUserResponse>>()
        response.value = BaseNetworkResult.Loading(true)
        authService.registration(registerUserRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                response.value = BaseNetworkResult.Loading(false)
            }
            .doOnError {
                response.value = BaseNetworkResult.Error(it.message)
            }
            .doOnNext {
                response.value = BaseNetworkResult.Success(it)
            }
            .subscribe()
        return response
    }

    override fun login(loginRequest: LoginRequest): LiveData<BaseNetworkResult<LoginResponse>> {
        val response = MutableLiveData<BaseNetworkResult<LoginResponse>>()
        response.value = BaseNetworkResult.Loading(true)
        authService.login(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                response.value = BaseNetworkResult.Loading(false)
            }
            .doOnError {
                response.value = BaseNetworkResult.Error(it.message)
            }
            .doOnNext {
                response.value = BaseNetworkResult.Success(it)
            }
            .subscribe()
        return response
    }
}