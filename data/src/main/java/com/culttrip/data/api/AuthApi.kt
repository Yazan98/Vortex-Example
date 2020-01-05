package com.culttrip.data.api

import com.culttrip.data.models.VortexResponse
import com.culttrip.data.models.body.LoginBody
import com.culttrip.data.models.body.RegisterBody
import com.culttrip.data.models.response.AuthResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 2:12 PM
 */

interface AuthApi {

    @POST("accounts/login")
    fun login(@Body body: LoginBody): Observable<VortexResponse<AuthResponse>>

    @POST("accounts")
    fun register(@Body body: RegisterBody): Observable<VortexResponse<AuthResponse>>

}