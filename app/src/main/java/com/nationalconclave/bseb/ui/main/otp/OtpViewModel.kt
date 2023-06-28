package com.nationalconclave.bseb.ui.main.otp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nationalconclave.bseb.App
import com.nationalconclave.bseb.utils.NetworkService
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class OtpViewModel : ViewModel() {


    init {
        App().component.inject(this)
    }
    @Inject
    lateinit var service: NetworkService






    var publishObjectOtpResponse = PublishSubject.create<OtpResponse>()
    var publishObjectOtpVerifiedResponse = PublishSubject.create<OtpVerifiedResponse>()
    var showToast = PublishSubject.create<String>()


    suspend fun sendOtp(request: OtpRequest): OtpResponse? {
        try{
            val data =  viewModelScope.async(Dispatchers.IO){
                service.sendOtp(request)
            }.await()
            if(data.isSuccessful){
                return data.body()?.data
            }
            else{
                showToast.onNext( data.body()?.error?.message?:"Something went wrong")
            }


        }
        catch (e:Exception){
            Log.e("exception","")
        }
        return null


    }

    fun triggerOtp(request: OtpRequest) {
        val job = viewModelScope.launch{
            val it = sendOtp(request)
            it?.let {
                updateData(it)
            }


        }


    }

    private fun updateData(otpRes: OtpResponse) {

        publishObjectOtpResponse.onNext(otpRes )


    }



    suspend fun verifyOtp(request: OtpVerifyRequest): OtpVerifiedResponse? {
        try{
            val data =  viewModelScope.async(Dispatchers.IO){
                service.validateOtp(request)
            }.await()
            if(data.isSuccessful){
                return data.body()?.data
            }
            else{
                showToast.onNext( data.body()?.error?.message?:"Invalid Otp")
            }

        }
        catch (e:Exception){
            Log.e("exception","")
        }
        return null


    }

    fun validateOtp(request: OtpVerifyRequest) {
        val job = viewModelScope.launch{
            val it = verifyOtp(request)
            it?.let {
                updateDataOtpVerified(it)
            }


        }


    }

    private fun updateDataOtpVerified(otpRes: OtpVerifiedResponse) {

        publishObjectOtpVerifiedResponse.onNext(otpRes )


    }


}