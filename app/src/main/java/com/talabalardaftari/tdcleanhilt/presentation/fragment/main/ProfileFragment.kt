package com.talabalardaftari.tdcleanhilt.presentation.fragment.main

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.talabalardaftari.tdcleanhilt.R
import com.talabalardaftari.tdcleanhilt.data.base.BaseNetworkResult
import com.talabalardaftari.tdcleanhilt.data.base.gone
import com.talabalardaftari.tdcleanhilt.data.base.hide
import com.talabalardaftari.tdcleanhilt.data.base.show
import com.talabalardaftari.tdcleanhilt.data.base.toast
import com.talabalardaftari.tdcleanhilt.data.main.model.request.AddNoteBookRequest
import com.talabalardaftari.tdcleanhilt.data.main.model.response.EducationInfo
import com.talabalardaftari.tdcleanhilt.data.main.model.response.StudentDTO
import com.talabalardaftari.tdcleanhilt.databinding.FragmentProfileBinding
import com.talabalardaftari.tdcleanhilt.presentation.fragment.BaseFragment
import com.talabalardaftari.tdcleanhilt.presentation.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate){
    private val viewmodel : MainViewModel by viewModels()
    private lateinit var datePickerDialog:DatePickerDialog
    private lateinit var meStudentDTO: StudentDTO
    override fun onViewCreate() {
        val studys = resources.getStringArray(R.array.Studys)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_menu_adapter, studys)
        binding.autoCompleteStudy.setAdapter(arrayAdapter)
        initDatePicker()
        binding.datePickerBtn.setOnClickListener {
            openDatePicker()
        }
        observer()
        viewmodel.me()
        binding.saqlashEdit.setOnClickListener {
            val studentDTO = StudentDTO(
                firstName = binding.ismEditText.text.toString(),
                email = binding.emailEditText.text.toString(),
                lastName = binding.familyaEditText.text.toString(),
                phoneNumber = binding.nomerEditText.text.toString(),
                studyType = binding.autoCompleteStudy.text.toString(),
                username = binding.userName.text.toString(),
                birthDate = binding.datePickerBtn.text.toString(),
                id = meStudentDTO.id,
                educationInfo = EducationInfo(
                    additionalProp1 = "additionalProp1",
                    additionalProp2 = "additionalProp2",
                    additionalProp3 = "additionalProp3"
                )
            )
            viewmodel.updateProfile(studentDTO)
        }
    }

    private fun observer() {
        viewmodel.me.observe(viewLifecycleOwner){state->
            when(state){
                is BaseNetworkResult.Error -> {
//                    toast(state.message)
                }
                is BaseNetworkResult.Loading -> {

                }
                is BaseNetworkResult.Success -> {
                    val data = state.data
                    if (data!=null){
                        meStudentDTO = data
                        binding.userName.text = data.username
                        binding.ismEditText.setText(data.firstName)
                        binding.familyaEditText.setText(data.lastName)
                        binding.nomerEditText.setText(data.phoneNumber)
                        if (data.birthDate!=null){
                            binding.datePickerBtn.text = data.birthDate
                        }
                        if (data.studyType!=null){
                            binding.autoCompleteStudy.setText(data.studyType)
                        }
                        binding.emailEditText.setText(data.email)
                    }else{
                        toast("Serverda xatolik yana bir bor urunib ko'ring")
                    }
                }
            }
        }
        viewmodel.updateProfile.observe(viewLifecycleOwner){state->
            when(state){
                is BaseNetworkResult.Error->{
                    toast(state.message)
                    binding.loaderSaqlash.gone()
                    binding.saqlashEdit.show()
                }
                is BaseNetworkResult.Loading->{
                    binding.loaderSaqlash.show()
                    binding.saqlashEdit.gone()
                }
                is BaseNetworkResult.Success->{
                    binding.loaderSaqlash.gone()
                    binding.saqlashEdit.show()
                    viewmodel.me()
                }
            }
        }
    }

    private fun initDatePicker(){
        val dateSetListener:DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val date:String = makeDateString(dayOfMonth,month+1,year)
            binding.datePickerBtn.text =date
        }
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val style = AlertDialog.THEME_HOLO_LIGHT
        datePickerDialog = DatePickerDialog(requireContext(),style,dateSetListener,year,month,day)
    }
    fun openDatePicker(){
        datePickerDialog.show()
    }

    private fun makeDateString(dayOfMonth: Int, month: Int, year: Int): String {
        return getMonthFormat(month)+" " + dayOfMonth + " " + year
    }

    private fun getMonthFormat(month: Int): String {
        return when(month){
            1->"YANVAR"
            2->"FEVRAL"
            3->"MART"
            4->"APREL"
            5->"MAY"
            6->"IYUN"
            7->"IYUL"
            8->"AVGUST"
            9->"SENTABR"
            10->"OKTABR"
            11->"NOYABR"
            12->"DEKABR"
            else->"YANVAR"
        }
    }
}