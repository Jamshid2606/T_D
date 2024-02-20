package com.talabalardaftari.tdcleanhilt.presentation.fragment.main

import androidx.fragment.app.viewModels
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.databinding.FragmentNoutbooksBinding
import com.talabalardaftari.tdcleanhilt.presentation.fragment.BaseFragment
import com.talabalardaftari.tdcleanhilt.presentation.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotebooksFragment : BaseFragment<FragmentNoutbooksBinding>(FragmentNoutbooksBinding::inflate){
    private val viewmodel : MainViewModel by viewModels()
    override fun onViewCreate() {
        observer()
        viewmodel.getNoteBooks(
            page = 0,
            size = 10,
            "",
            "",
            ""
        )
    }

    private fun observer() {
        viewmodel.getNoteBooks.observe(viewLifecycleOwner){state->
            when(state){
                is BaseNetworkResult.Error -> {

                }
                is BaseNetworkResult.Loading -> {

                }
                is BaseNetworkResult.Success -> {

                }
            }
        }
    }
}