package com.nationalconclave.bseb.ui.main.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nationalconclave.bseb.App
import com.nationalconclave.bseb.ui.main.model.Movie
import com.nationalconclave.bseb.utils.NetworkService
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.*
import javax.inject.Inject


class MainActivityViewModel : ViewModel() {
    @Inject
    lateinit var service: NetworkService
    var publishObject = PublishSubject.create<ArrayList<Movie?>>()

    suspend fun getList(search:String,start: Int): ArrayList<Movie?>? {
        try{
       val data =  viewModelScope.async(Dispatchers.IO){
            service.getMovieList(search, start)
        }.await()

        return data?.response?:ArrayList()
        }
        catch (e:Exception){
            return ArrayList()
            Log.e("exception","")
        }


    }

     fun getMovieList(search:String,start:Int) {
       val job = viewModelScope.launch{
           val it = getList(search,start)
           it?.let {
               updateData(it,start)
           }


       }


    }

    private fun updateData(it: ArrayList<Movie?>, offset: Int) {

        publishObject.onNext(it )


    }

    init {
        App().component.inject(this)
    }
}