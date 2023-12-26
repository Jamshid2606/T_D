package com.talabalardaftari.tdcleanhilt.data.base

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPref(context: Context) {

    private var preferences: SharedPreferences =
        context.getSharedPreferences("local_shared_pref", MODE_PRIVATE)

    private lateinit var editor: SharedPreferences.Editor

    ////////////////////////////////////////////////////////

    fun setToken(token:String){
        editor = preferences.edit()
        editor.putString("TOKEN",token)
        editor.apply()
    }
    fun getToken()=preferences.getString("TOKEN","")

    fun setEmail(token:String){
        editor = preferences.edit()
        editor.putString("EMAIL",token)
        editor.apply()
    }
    fun getEmail()=preferences.getString("EMAIL","")
    fun setName(token:String){
        editor = preferences.edit()
        editor.putString("NAME",token)
        editor.apply()
    }
    fun getName()=preferences.getString("NAME","")
}