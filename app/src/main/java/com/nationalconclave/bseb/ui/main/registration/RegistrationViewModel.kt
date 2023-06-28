package com.nationalconclave.bseb.ui.main.registration

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

class RegistrationViewModel : ViewModel() {
    init {
        App().component.inject(this)
    }
    @Inject
    lateinit var service: NetworkService




    fun registerUser(){

    }


    var publishObject = PublishSubject.create<RegistrationResponse>()
    var showToast = PublishSubject.create<String>()

    suspend fun register(request: RegistrationRequest): RegistrationResponse? {
        try{
            val data =  viewModelScope.async(Dispatchers.IO){
                service.registerUser(request)
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

    fun registerUser(request: RegistrationRequest) {
        val job = viewModelScope.launch{
            val it = register(request)
            it?.let {
                updateData(it)
            }


        }


    }

    private fun updateData(registrationResponse: RegistrationResponse) {

        publishObject.onNext(registrationResponse )


    }


}

