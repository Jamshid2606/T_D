package com.talabalardaftari.tdcleanhilt.presentation.fragment.auth

import android.util.Patterns
import android.widget.Button
import android.widget.ProgressBar
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.talabalardaftari.tdcleanhilt.data.base.toast
import com.talabalardaftari.tdcleanhilt.databinding.FragmentRegisterBinding
import com.talabalardaftari.tdcleanhilt.presentation.fragment.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private lateinit var registerBtnClickListener: (progress:ProgressBar,register:Button,name:String, email:String) -> Unit

    fun registerBtnClickListener(listener: ((progress: ProgressBar, register: Button, name:String, email:String) -> Unit)) {
        registerBtnClickListener = listener
    }
    override fun onViewCreate() {
        binding.register.setOnClickListener {
            if (validation()){
                registerBtnClickListener.invoke(binding.progressRGG,binding.register,binding.nameEditText.text.toString(), binding.emailEditText.text.toString())
            }
        }
    }
    private fun validation(): Boolean {
        if (binding.nameEditText.text.toString() == "") {
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.nameTextInput)
            return false
        } else {
            if (binding.nameEditText.text.toString().length < 3) {
                YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.nameTextInput)
                toast("Ism kamida 3 ta belgidan iborat bo'lishi kerak")
                return false
            }
        }
        if (binding.emailEditText.text.toString() == "") {
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.phoneTextInput)
            return false
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailEditText.text.toString()).matches()) {
                YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.phoneTextInput)
                return false
            }
        }
        return true
    }
}