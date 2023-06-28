package com.nationalconclave.bseb.ui.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nationalconclave.bseb.App
import com.nationalconclave.bseb.ui.main.otp.OtpVerifiedResponse
import com.nationalconclave.bseb.utils.NetworkService
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    init {
        App().component.inject(this)
    }
    @Inject
    lateinit var service: NetworkService


    var publishObjectResponse = PublishSubject.create<UserResponse>()
    var publishObjectOtpVerifiedResponse = PublishSubject.create<OtpVerifiedResponse>()
    var showToast = PublishSubject.create<String>()


    suspend fun getUser(token: String): UserResponse? {
        try{
            val data =  viewModelScope.async(Dispatchers.IO){
                service.getUserData(token)
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

    fun getUserData(token: String) {
        val job = viewModelScope.launch{
            val it = getUser(token)
            it?.let {
                updateData(it)
            }


        }


    }

    private fun updateData(otpRes: UserResponse) {

        publishObjectResponse.onNext(otpRes )


    }

}