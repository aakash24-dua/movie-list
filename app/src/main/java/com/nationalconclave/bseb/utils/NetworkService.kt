package com.nationalconclave.bseb.utils


import com.google.gson.GsonBuilder
import com.nationalconclave.bseb.data.Constants
import com.nationalconclave.bseb.ui.main.otp.OtpRequest
import com.nationalconclave.bseb.ui.main.otp.OtpVerifyRequest
import com.nationalconclave.bseb.ui.main.registration.RegistrationRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class NetworkService {



    private val searchApi: SearchApi by lazy {
        val interceptor = HttpLoggingInterceptor()
        var okHttpClient: OkHttpClient? = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor).build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        retrofit.create(SearchApi::class.java)
    }

    suspend fun getMovieList( search:String,page: Int) = searchApi.getMovieList(search,page)
    suspend fun getMovieDetails( id: String) = searchApi.getMovieDetails(id)

    suspend fun registerUser(request: RegistrationRequest) = searchApi.registerUser(request)
    suspend fun updateUser(request: RegistrationRequest,token: String) = searchApi.updateUser(" Bearer $token",request)
    suspend fun sendOtp(request: OtpRequest) = searchApi.sendOtp(request)
    suspend fun validateOtp(request: OtpVerifyRequest) = searchApi.verifyOtp(request)
    suspend fun getUserData(token:String) = searchApi.getUserData(" Bearer $token")

}