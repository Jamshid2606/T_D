package com.talabalardaftari.tdcleanhilt.data.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.main.model.request.AddNoteBookRequest
import com.talabalardaftari.tdcleanhilt.data.main.model.response.AddNoteBookResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.SaveAttachmentResponse
import com.talabalardaftari.tdcleanhilt.domain.repository.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody
import retrofit2.HttpException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val mainService: MainService) :MainRepository{
    @SuppressLint("CheckResult")
    override fun saveAttachment(imageBody: MultipartBody.Part): LiveData<BaseNetworkResult<SaveAttachmentResponse>> {
        val responsse = MutableLiveData<BaseNetworkResult<SaveAttachmentResponse>>()
        responsse.value = BaseNetworkResult.Loading(true)
        mainService.saveAttachment(imageBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                responsse.value = BaseNetworkResult.Loading(false)
            }
            .subscribe(
                {
                    responsse.value = BaseNetworkResult.Success(it)
                },
                {
                    responsse.value = BaseNetworkResult.Error(it.message)
                }
            )
        return responsse
    }

    @SuppressLint("CheckResult")
    override fun addNoteBook(addNoteBookRequest: AddNoteBookRequest): LiveData<BaseNetworkResult<AddNoteBookResponse>> {
        val responsse = MutableLiveData<BaseNetworkResult<AddNoteBookResponse>>()
        responsse.value = BaseNetworkResult.Loading(true)
        mainService.addNoteBook(addNoteBookRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                responsse.value = BaseNetworkResult.Loading(false)
            }
            .subscribe(
                {
                    responsse.value = BaseNetworkResult.Success(it)
                },
                {
                    if ((it as HttpException).code()==500){
                        responsse.value = BaseNetworkResult.Error("Bunday rasmdan avval yangi davlar yaratishda ishlatgansiz")
                    }else{
                        responsse.value = BaseNetworkResult.Error("Error code: ${it.code()} Serverda xatolik:${it.message()}")
                    }
                }
            )
        return responsse
    }
}