package com.talabalardaftari.tdcleanhilt.presentation.fragment.auth

import android.widget.Button
import android.widget.ProgressBar
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.talabalardaftari.tdcleanhilt.databinding.FragmentLoginBinding
import com.talabalardaftari.tdcleanhilt.presentation.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private lateinit var loginBtnClickListener:(username:String, password:String,progress:ProgressBar, login:Button) -> Unit

    fun loginBtnClickListener(listener:((username:String, password:String,progress:ProgressBar, login:Button) -> Unit)) {
        loginBtnClickListener = listener
    }
    override fun onViewCreate() {
        binding.login.setOnClickListener {
            if (validation()){
                loginBtnClickListener.invoke(binding.usernameEditText.text.toString(), binding.passwordEditText.text.toString(), binding.progressLGG, binding.login)
            }
        }
    }
    fun validation():Boolean{
        if (binding.usernameEditText.text==null){
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.usernameEditText)
            return false
        }else if (binding.passwordEditText.text==null){
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.passwordEditText)
            return false
        }
        return true
    }
}