package com.talabalardaftari.tdcleanhilt.presentation.fragment.auth

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.talabalardaftari.tdcleanhilt.R
import com.talabalardaftari.tdcleanhilt.data.auth.model.request.VerifyEmailRequest
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.base.SharedPref
import com.talabalardaftari.tdcleanhilt.data.base.hide
import com.talabalardaftari.tdcleanhilt.data.base.show
import com.talabalardaftari.tdcleanhilt.data.base.snackbar
import com.talabalardaftari.tdcleanhilt.data.base.toast
import com.talabalardaftari.tdcleanhilt.databinding.FragmentConfirmBinding
import com.talabalardaftari.tdcleanhilt.presentation.fragment.BaseFragment
import com.talabalardaftari.tdcleanhilt.presentation.vm.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmFragment : BaseFragment<FragmentConfirmBinding>(FragmentConfirmBinding::inflate) {
    private val viewmodel : AuthViewModel by viewModels()
    val shared by lazy {
        SharedPref(requireContext())
    }
    override fun onViewCreate() {
        observer()
        binding.tasdiqlash.setOnClickListener {
            if (validation()){
                viewmodel.verifyEmail(VerifyEmailRequest(
                    code = binding.code.text.toString(),
                    email = shared.getEmail().toString()
                ))
            }
        }
//        requireActivity()
//            .onBackPressedDispatcher
//            .addCallback(this, object :OnBackPressedCallback(true){
//                override fun handleOnBackPressed() {
//                    Log.d("Confirm Fragment", "Fragment back pressed invoked")
//                    navController.popBackStack()
//                }
//            })
    }

    private fun observer() {
        viewmodel.verifyEmail.observe(viewLifecycleOwner){state->
            when(state){
                is BaseNetworkResult.Error -> {
                    binding.tasdiqlash.show()
                    binding.progressRG.hide()
                    snackbar(state.message.toString(), binding.root)
                }
                is BaseNetworkResult.Loading -> {
                    binding.tasdiqlash.hide()
                    binding.progressRG.show()
                }
                is BaseNetworkResult.Success -> {
                    binding.progressRG.hide()
                    snackbar("Success", binding.root)
                    navController.navigate(R.id.action_confirmFragment_to_createUserFragment)
                }
            }
        }
    }

    private fun validation():Boolean{
        return binding.code.length()==6
    }
}