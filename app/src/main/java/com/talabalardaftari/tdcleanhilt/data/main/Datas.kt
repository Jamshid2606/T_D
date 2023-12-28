package com.talabalardaftari.tdcleanhilt.data.main

import com.talabalardaftari.tdcleanhilt.data.base.SharedPref
import okhttp3.Interceptor
import java.sql.Struct

object Datas {
    private var authorizationToken = ""
    fun getAuthorizationToken() : String{
        return authorizationToken
    }
    fun setAuthorizationToken(token:String){
        authorizationToken = token
    }
    class BearerTokenInterceptor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $authorizationToken")  // Bearer Tokenni qo'shish
                .build()
            return chain.proceed(newRequest)
        }
    }
}