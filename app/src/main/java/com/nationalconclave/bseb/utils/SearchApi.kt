package com.nationalconclave.bseb.utils

import com.nationalconclave.bseb.ui.main.home.UserResponse
import com.nationalconclave.bseb.ui.main.model.MovieResponse
import com.nationalconclave.bseb.ui.main.otp.OtpRequest
import com.nationalconclave.bseb.ui.main.otp.OtpResponse
import com.nationalconclave.bseb.ui.main.otp.OtpVerifiedResponse
import com.nationalconclave.bseb.ui.main.otp.OtpVerifyRequest
import com.nationalconclave.bseb.ui.main.registration.RegistrationRequest
import com.nationalconclave.bseb.ui.main.registration.RegistrationResponse
import com.nationalconclave.bseb.ui.movieDetail.model.MovieDetailsResponse
import retrofit2.Response
import retrofit2.http.*

internal interface SearchApi {



    @GET("?apikey=b9bd48a6&type=movie")
     suspend fun getMovieList(
        @Query("s") s:String,
        @Query("page") page:Int
    ): MovieResponse?


    @GET("?apikey=b9bd48a6")
    suspend fun getMovieDetails(
        @Query("i") id:String
    ): MovieDetailsResponse?


    @POST("/register")
    suspend fun registerUser(
        @Body request: RegistrationRequest
    ): Response<GenericResponse<RegistrationResponse>>


    @PUT("/users")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Body request: RegistrationRequest
    ): Response<GenericResponse<RegistrationResponse>>


    @POST("/otp/send")
    suspend fun sendOtp(
        @Body request: OtpRequest
    ): Response<GenericResponse<OtpResponse>>


    @POST("/otp/verify")
    suspend fun verifyOtp(
        @Body request: OtpVerifyRequest
    ): Response<GenericResponse<OtpVerifiedResponse>>


    @GET("/users/me")
    suspend fun getUserData(
        @Header("Authorization") token: String
    ): Response<GenericResponse<UserResponse>>

}
