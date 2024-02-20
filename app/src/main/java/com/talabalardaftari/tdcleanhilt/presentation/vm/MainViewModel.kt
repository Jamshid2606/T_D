package com.talabalardaftari.tdcleanhilt.presentation.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.main.model.request.AddNoteBookRequest
import com.talabalardaftari.tdcleanhilt.data.main.model.response.AddNoteBookResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.GetNoteBooksResponseItem
import com.talabalardaftari.tdcleanhilt.data.main.model.response.SaveAttachmentResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.StudentDTO
import com.talabalardaftari.tdcleanhilt.domain.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainUseCase: MainUseCase) :ViewModel() {
    private val _saveAttachment = MutableLiveData<BaseNetworkResult<SaveAttachmentResponse>>()
    val saveAttachment : LiveData<BaseNetworkResult<SaveAttachmentResponse>>
        get() = _saveAttachment
    private val _addNoteBook = MutableLiveData<BaseNetworkResult<AddNoteBookResponse>>()
    val addNoteBook : LiveData<BaseNetworkResult<AddNoteBookResponse>>
        get() = _addNoteBook
    private val _me = MutableLiveData<BaseNetworkResult<StudentDTO>>()
    val me : LiveData<BaseNetworkResult<StudentDTO>>
        get() = _me
    private val _updateProfile = MutableLiveData<BaseNetworkResult<StudentDTO>>()
    val updateProfile : LiveData<BaseNetworkResult<StudentDTO>>
        get() = _updateProfile
    private val _getNoteBooks = MutableLiveData<BaseNetworkResult<List<GetNoteBooksResponseItem>>>()
    val getNoteBooks : LiveData<BaseNetworkResult<List<GetNoteBooksResponseItem>>>
        get() = _getNoteBooks
    fun saveAttachment(imageBody: MultipartBody.Part){
        _saveAttachment.value = BaseNetworkResult.Loading(true)
        mainUseCase.saveAttachment(imageBody).observeForever {
            when(it){
                is BaseNetworkResult.Error -> {
                    Log.d("MainViewModel, saveAttachment Error: ", "${it.message}")
                    _saveAttachment.value = BaseNetworkResult.Error(it.message)
                }
                is BaseNetworkResult.Loading -> {
                    _saveAttachment.value = BaseNetworkResult.Loading(it.isLoading)
                }
                is BaseNetworkResult.Success -> {
                    if (it.data!=null){
                        _saveAttachment.value = BaseNetworkResult.Success(it.data)
                    }else{
                        Log.d("MainViewModel, saveAttachment Error: ", "Response data is null!!!")
                        _saveAttachment.value = BaseNetworkResult.Error(it.message)
                    }
                }
            }
        }
    }
    fun addNoteBook(addNoteBookRequest: AddNoteBookRequest){
        _addNoteBook.value = BaseNetworkResult.Loading(true)
        mainUseCase.addNoteBook(addNoteBookRequest).observeForever {
            when(it){
                is BaseNetworkResult.Error -> {
                    Log.d("MainViewModel, addNoteBook Error: ", "${it.message}")
                    _addNoteBook.value = BaseNetworkResult.Error(it.message)
                }
                is BaseNetworkResult.Loading -> {
                    _addNoteBook.value = BaseNetworkResult.Loading(it.isLoading)
                }
                is BaseNetworkResult.Success -> {
                    if (it.data!=null){
                        _addNoteBook.value = BaseNetworkResult.Success(it.data)
                    }else{
                        Log.d("MainViewModel, addNoteBook Error: ", "Response data is null!!!")
                        _addNoteBook.value = BaseNetworkResult.Error(it.message)
                    }
                }
            }
        }
    }
    fun me(){
        _me.value = BaseNetworkResult.Loading(true)
        mainUseCase.me().observeForever {
            when(it){
                is BaseNetworkResult.Error->{
                    Log.d("MainViewModel, me Error: ", "${it.message}")
                    _me.value = BaseNetworkResult.Error(it.message)
                }
                is BaseNetworkResult.Loading->{
                    _me.value = BaseNetworkResult.Loading(it.isLoading)
                }
                is BaseNetworkResult.Success->{
                    if (it.data!=null){
                        _me.value = BaseNetworkResult.Success(it.data)
                    }else{
                        Log.d("MainViewModel, me Error: ", "Response data is null!!!")
                        _me.value = BaseNetworkResult.Error(it.message)
                    }
                }
            }
        }
    }
    fun updateProfile(studentDTO: StudentDTO){
        _updateProfile.value = BaseNetworkResult.Loading(true)
        mainUseCase.updateProfile(studentDTO).observeForever {
            when(it){
                is BaseNetworkResult.Error->{
                    Log.d("MainViewModel, me Error: ", "${it.message}")
                    _updateProfile.value = BaseNetworkResult.Error(it.message)
                }
                is BaseNetworkResult.Loading->{
                    _updateProfile.value = BaseNetworkResult.Loading(it.isLoading)
                }
                is BaseNetworkResult.Success->{
                    if (it.data!=null){
                        _updateProfile.value = BaseNetworkResult.Success(it.data)
                    }else{
                        Log.d("MainViewModel, me Error: ", "Response data is null!!!")
                        _updateProfile.value = BaseNetworkResult.Error(it.message)
                    }
                }
            }
        }
    }
    fun getNoteBooks(page:Int,size:Int,sortBy:String,sortDirection:String,search:String){
        _getNoteBooks.value = BaseNetworkResult.Loading(true)
        mainUseCase.getNoteBooks(page, size, sortBy, sortDirection, search).observeForever {
            when(it){
                is BaseNetworkResult.Error -> {
                    Log.d("MainViewModel, me Error: ", "${it.message}")
                    _getNoteBooks.value = BaseNetworkResult.Error(it.message)
                }
                is BaseNetworkResult.Loading ->  {
                    _getNoteBooks.value = BaseNetworkResult.Loading(it.isLoading)
                }
                is BaseNetworkResult.Success -> {
                    if (it.data!=null){
                        _getNoteBooks.value = BaseNetworkResult.Success(it.data)
                    }else{
                        _getNoteBooks.value = BaseNetworkResult.Error("Siz hali daftar yaratmagansiz")
                    }
                }
            }
        }
    }
}