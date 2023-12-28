package com.talabalardaftari.tdcleanhilt.presentation.fragment.main

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.talabalardaftari.tdcleanhilt.R
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.base.SharedPref
import com.talabalardaftari.tdcleanhilt.data.base.hide
import com.talabalardaftari.tdcleanhilt.data.base.show
import com.talabalardaftari.tdcleanhilt.data.base.toast
import com.talabalardaftari.tdcleanhilt.data.main.model.request.AddNoteBookRequest
import com.talabalardaftari.tdcleanhilt.databinding.FragmentAddNoteBookBinding
import com.talabalardaftari.tdcleanhilt.presentation.adapters.AddNoteBookAdapter
import com.talabalardaftari.tdcleanhilt.presentation.fragment.BaseFragment
import com.talabalardaftari.tdcleanhilt.presentation.vm.AuthViewModel
import com.talabalardaftari.tdcleanhilt.presentation.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.InputStream

@AndroidEntryPoint
class AddNoteBookFragment : BaseFragment<FragmentAddNoteBookBinding>(FragmentAddNoteBookBinding::inflate) {
    private val viewmodel : MainViewModel by viewModels()
    private lateinit var adapter: AddNoteBookAdapter
    private var imagePath:String=""
    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            handleImageResult(result.data)
        }
    }
    override fun onViewCreate() {
        initAdapter()
        observer()
        binding.saqlash.setOnClickListener {
            if (validation()){
                toast(imagePath)
                val imageFile = File(imagePath.toUri().path.toString())
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
                val imageBody = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
                viewmodel.saveAttachment(imageBody)
            }
        }
    }

    private fun observer() {
        viewmodel.saveAttachment.observe(viewLifecycleOwner){state->
            when(state){
                is BaseNetworkResult.Error -> {
                    toast(state.message)
                    binding.saqlash.show()
                    binding.progressLGG.hide()
                }
                is BaseNetworkResult.Loading -> {
                    binding.progressLGG.show()
                    binding.saqlash.hide()
                }
                is BaseNetworkResult.Success -> {
                    binding.progressLGG.hide()
                    binding.saqlash.show()
                    val data = state.data
                    if (data!=null){
                        viewmodel.addNoteBook(AddNoteBookRequest(
                            name = binding.nomEditText.text.toString(),
                            attachmentId = data.id
                        ))
                    }else{
                        toast("Serverda xatolik yana bir bor urunib ko'ring")
                    }
                }
            }
        }
        viewmodel.addNoteBook.observe(viewLifecycleOwner){state->
            when(state){
                is BaseNetworkResult.Error -> {
                    binding.progressLGG.hide()
                    binding.saqlash.show()
                    toast(state.message)
                }
                is BaseNetworkResult.Loading -> {
                    binding.progressLGG.show()
                    binding.saqlash.hide()
                }
                is BaseNetworkResult.Success -> {
                    binding.progressLGG.hide()
                    binding.saqlash.show()
                    toast("Success")
                    navController.navigate(R.id.notebooksFragment)
                }
            }
        }
    }

    private fun validation(): Boolean {
        if (binding.nomEditText.text.toString().length<2){
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.nomTextInput)
            return false
        }else if (imagePath==""){
            YoYo.with(Techniques.Wobble).duration(500).repeat(0).playOn(binding.list)
            return false
        }
        return true
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }
    private fun handleImageResult(data: Intent?) {
        if (data != null && data.data != null) {
            // Rasm manzili
            imagePath = data.data.toString()
            // Rasmni ImageView ga joylash
            // (Bu joylashni o'zgartiring, masalan, Glide yoki Picasso bilan)
            Glide.with(this)
                .load(data.data)
                .into(binding.imgUr)
            binding.imgUr.show()
        }
    }
    private fun initAdapter(){
        adapter = AddNoteBookAdapter(){position->
            if (position==0){
                pickImageFromGallery()
            }
        }
        val list = ArrayList<Int>()
        list.add(R.drawable.add_notebook_list0)
        list.add(R.drawable.add_notebook_list1)
        list.add(R.drawable.add_notebook_list2)
        list.add(R.drawable.add_notebook_list3)
        list.add(R.drawable.add_notebook_list4)
        list.add(R.drawable.add_notebook_list5)
        list.add(R.drawable.add_notebook_list6)
        list.add(R.drawable.add_notebook_list7)
        list.add(R.drawable.add_notebook_list8)
        adapter.setData(list)
        binding.list.adapter = adapter
    }
}