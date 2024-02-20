package com.talabalardaftari.tdcleanhilt.data.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.talabalardaftari.tdcleanhilt.data.auth.model.response.getNoteBooksResponse.GetNoteBooksResponse
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.main.model.request.AddNoteBookRequest
import com.talabalardaftari.tdcleanhilt.data.main.model.response.AddNoteBookResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.GetNoteBooksResponseItem
import com.talabalardaftari.tdcleanhilt.data.main.model.response.SaveAttachmentResponse
import com.talabalardaftari.tdcleanhilt.data.main.model.response.StudentDTO
import com.talabalardaftari.tdcleanhilt.domain.repository.MainRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody
import retrofit2.HttpException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(private val mainService: MainService) :MainRepository{
    @SuppressLint("CheckResult")
    override fun saveAttachment(file: MultipartBody.Part): LiveData<BaseNetworkResult<SaveAttachmentResponse>> {
        val responsse = MutableLiveData<BaseNetworkResult<SaveAttachmentResponse>>()
        responsse.value = BaseNetworkResult.Loading(true)
        mainService.saveAttachment(file)
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
                    responsse.value = BaseNetworkResult.Error("Error: ${it.printStackTrace()} Serverda xatolik: ${it.message}")
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
                        responsse.value = BaseNetworkResult.Error("Bunday rasmdan avval yangi daftar yaratishda ishlatgansiz")
                    }else{
                        responsse.value = BaseNetworkResult.Error("Error code: ${it.code()} Serverda xatolik:${it.message()}")
                    }
                    responsse.value = BaseNetworkResult.Error("Error code: ${it.code()} Serverda xatolik: ${it.message()}")
                }
            )
        return responsse
    }

    @SuppressLint("CheckResult")
    override fun getNoteBooks(
        page: Int,
        size: Int,
        sortBy: String,
        sortDirection: String,
        search: String
    ): LiveData<BaseNetworkResult<List<GetNoteBooksResponseItem>>> {
        val responsse = MutableLiveData<BaseNetworkResult<List<GetNoteBooksResponseItem>>>()
        responsse.value = BaseNetworkResult.Loading(true)
        mainService.getNoteBooks(page, size, sortBy, sortDirection, search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                responsse.value = BaseNetworkResult.Loading(false)
            }
            .subscribe(
                {
                    Log.d("LLIISSTT", it.toString())
                    responsse.value = BaseNetworkResult.Success(it)
                },
                {
                    responsse.value = BaseNetworkResult.Error("Error code: ${(it as HttpException).code()} Serverda xatolik: ${it.message()}")
                }
            )
        return responsse
    }

    @SuppressLint("CheckResult")
    override fun me(): LiveData<BaseNetworkResult<StudentDTO>> {
        val ressponse = MutableLiveData<BaseNetworkResult<StudentDTO>>()
        ressponse.value = BaseNetworkResult.Loading(true)
        mainService.me()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                ressponse.value = BaseNetworkResult.Loading(false)
            }
            .subscribe(
                {
                    ressponse.value = BaseNetworkResult.Success(it)
                },{
                    ressponse.value = BaseNetworkResult.Error("Error code: ${(it as HttpException).code()} Serverda xatolik: ${it.message()}")
                }
            )
        return ressponse
    }

    @SuppressLint("CheckResult")
    override fun updateProfile(studentDTO: StudentDTO): LiveData<BaseNetworkResult<StudentDTO>> {
        val responsse = MutableLiveData<BaseNetworkResult<StudentDTO>>()
        responsse.value = BaseNetworkResult.Loading(true)
        mainService.updateProfile(studentDTO)
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
                    responsse.value = BaseNetworkResult.Error("Error code: ${(it as HttpException).code()} Serverda xatolik: ${it.message()}")
                }
            )
        return responsse
    }
}