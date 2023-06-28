package com.nationalconclave.bseb.ui.main.sightseeingregistration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nationalconclave.bseb.App
import com.nationalconclave.bseb.ui.main.registration.RegistrationRequest
import com.nationalconclave.bseb.ui.main.registration.RegistrationResponse
import com.nationalconclave.bseb.utils.NetworkService
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class SightSeeingViewModel : ViewModel() {

    init {
        App().component.inject(this)
    }
    @Inject
    lateinit var service: NetworkService





    var publishObject = PublishSubject.create<RegistrationResponse>()
    var showToast = PublishSubject.create<String>()

    suspend fun register(request: RegistrationRequest,token: String): RegistrationResponse? {
        try{
            val data =  viewModelScope.async(Dispatchers.IO){
                service.updateUser(request,token)
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

    fun updateUser(request: RegistrationRequest,token:String) {
        val job = viewModelScope.launch{
            val it = register(request,token)
            it?.let {
                updateData(it)
            }


        }


    }

    private fun updateData(registrationResponse: RegistrationResponse) {

        publishObject.onNext(registrationResponse )


    }


}