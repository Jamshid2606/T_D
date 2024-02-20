package com.talabalardaftari.tdcleanhilt.presentation.fragment.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.talabalardaftari.tdcleanhilt.R
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.base.hide
import com.talabalardaftari.tdcleanhilt.data.base.show
import com.talabalardaftari.tdcleanhilt.data.base.toast
import com.talabalardaftari.tdcleanhilt.data.main.model.request.AddNoteBookRequest
import com.talabalardaftari.tdcleanhilt.databinding.FragmentAddNoteBookBinding
import com.talabalardaftari.tdcleanhilt.presentation.adapters.AddNoteBookAdapter
import com.talabalardaftari.tdcleanhilt.presentation.fragment.BaseFragment
import com.talabalardaftari.tdcleanhilt.presentation.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class AddNoteBookFragment : BaseFragment<FragmentAddNoteBookBinding>(FragmentAddNoteBookBinding::inflate) {
    private val viewmodel : MainViewModel by viewModels()
    private lateinit var adapter: AddNoteBookAdapter
    private var imagePath:String=""
    private lateinit var imagePickerLauncher : ActivityResultLauncher<Intent>
    override fun onViewCreate() {
        initAdapter()
        observer()
        addNoteResultLauncher()
        binding.saqlash.setOnClickListener {
            if (validation()){
                val filesDir = requireContext().filesDir
                val file = File(filesDir,"image.png")
                val inputStream = requireActivity().contentResolver.openInputStream(imagePath.toUri())
                val outputStream = FileOutputStream(file)
                inputStream!!.copyTo(outputStream)
                val requestbody = file.asRequestBody("image/*".toMediaTypeOrNull())
                val part = MultipartBody.Part.createFormData("file",file.name,requestbody)
                viewmodel.saveAttachment(part)
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
    private fun addNoteResultLauncher(){
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                try {
                    imagePath = result.data!!.data.toString()
                    Glide.with(this)
                        .load(imagePath)
                        .into(binding.imgUr)
                    binding.imgUr.show()
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.setType("image/*")
        imagePickerLauncher.launch(galleryIntent)
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        imagePickerLauncher.launch(intent)
    }
    private fun checkStoragePermissionAndGetImage(){
        if (ActivityCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }else{
            pickImageFromGallery()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickImageFromGallery()
            }else{
                toast("Storage permission is denided, please allow permission to get image")
            }
        }
    }
    private fun initAdapter(){
        adapter = AddNoteBookAdapter { position->
            if (position==0){
                checkStoragePermissionAndGetImage()
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