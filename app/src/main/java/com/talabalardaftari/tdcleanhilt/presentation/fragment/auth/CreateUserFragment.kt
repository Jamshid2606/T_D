package com.talabalardaftari.tdcleanhilt.presentation.fragment.auth

import android.content.Intent
import androidx.fragment.app.viewModels
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.LoginRequest
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.RegisterUserRequest
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.base.SharedPref
import com.talabalardaftari.tdcleanhilt.data.base.hide
import com.talabalardaftari.tdcleanhilt.data.base.show
import com.talabalardaftari.tdcleanhilt.data.base.toast
import com.talabalardaftari.tdcleanhilt.data.base.validatePhoneNumber
import com.talabalardaftari.tdcleanhilt.databinding.FragmentCreateUserBinding
import com.talabalardaftari.tdcleanhilt.presentation.activity.MainActivity
import com.talabalardaftari.tdcleanhilt.presentation.fragment.BaseFragment
import com.talabalardaftari.tdcleanhilt.presentation.vm.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateUserFragment : BaseFragment<FragmentCreateUserBinding>(FragmentCreateUserBinding::inflate) {
    val shared by lazy {
        SharedPref(requireContext())
    }
    val viewmodel : AuthViewModel by viewModels()
    override fun onViewCreate() {
        observer()
        binding.yaratish.setOnClickListener {
            if (validation()){
                val registerUserRequest = RegisterUserRequest(
                    email = shared.getEmail().toString(),
                    firstName = shared.getName().toString(),
                    lastName = binding.lastNameEditText.text.toString(),
                    password = binding.password1EditText.text.toString(),
                    phoneNumber = binding.phoneNumberEditText.text.toString(),
                    username = binding.userNameEditText.text.toString()
                )
                viewmodel.registration(registerUserRequest)
            }
        }
    }

    private fun observer() {
        viewmodel.registration.observe(viewLifecycleOwner){state->
            when(state){
                is BaseNetworkResult.Error -> {
                    binding.yaratish.show()
                    binding.progressRG.hide()
                    toast(state.message)
                }
                is BaseNetworkResult.Loading -> {
                    binding.yaratish.hide()
                    binding.progressRG.show()
                }
                is BaseNetworkResult.Success -> {
                    viewmodel.login(LoginRequest(
                        password = binding.password1EditText.text.toString(),
                        username = binding.userNameEditText.text.toString()
                    ))
                }
            }
        }
        viewmodel.login.observeForever {state->
            when(state){
                is BaseNetworkResult.Error -> {
                    binding.yaratish.show()
                    binding.progressRG.hide()
                    toast(state.message)
                }
                is BaseNetworkResult.Loading -> {
                    binding.yaratish.hide()
                    binding.progressRG.show()
                }
                is BaseNetworkResult.Success -> {
                    binding.yaratish.show()
                    binding.progressRG.hide()
                    if (state.data!=null){
                        shared.setToken(state.data.id_token)
                    }
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    requireActivity().startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }

    private fun validation():Boolean{
        if (binding.userNameEditText.text.toString().length<6){
            toast("Username kamida 6 belgi")
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.userNameEditText)
            return false
        }else if (binding.lastNameEditText.text.toString().isEmpty()){
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.lastNameEditText)
            toast("Familya bo'sh bo'lishi mumkin emas")
            return false
        }else if (binding.phoneNumberEditText.text.toString().validatePhoneNumber()!="true"){
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.phoneNumberEditText)
            toast(binding.phoneNumberEditText.text.toString().validatePhoneNumber())
            return false
        }else if (binding.password1EditText.length()<8){
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.password1EditText)
            toast("Parol uzunligi 8 yoki 8+ belgidan iborat bo'lishi kerak")
            return false
        }else if (binding.password1EditText.text.toString()!=binding.password2EditText.text.toString()){
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.password2EditText)
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.password1EditText)
            toast("Siz parollaringizni noto'g'ri kiritdingiz.\n ikkala parol bir xil bo'lishi kerak")
            return false
        }
        return true
    }

}