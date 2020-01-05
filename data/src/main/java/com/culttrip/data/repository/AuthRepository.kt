package com.culttrip.data.repository

import com.culttrip.data.CulttripRepository
import com.culttrip.data.api.AuthApi
import com.culttrip.data.models.VortexResponse
import com.culttrip.data.models.body.LoginBody
import com.culttrip.data.models.body.RegisterBody
import com.culttrip.data.models.response.AuthResponse
import io.reactivex.Observable
import io.vortex.android.models.VortexServiceProviderType
import io.vortex.android.rx.VortexRequestProvider
import javax.inject.Inject
import kotlin.math.log

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 2:11 PM
 */

open class AuthRepository @Inject constructor() : CulttripRepository<AuthApi>() {

    private val provider:  VortexRequestProvider<VortexResponse<AuthResponse>> by lazy {
        VortexRequestProvider<VortexResponse<AuthResponse>>()
    }

    override suspend fun getService(): AuthApi {
        return getLoggerReactiveRetrofit(getBaseUrl()).create(AuthApi::class.java)
    }

    suspend fun login(loginBody: LoginBody): Observable<VortexResponse<AuthResponse>> {
        return provider.getObservable(getService().login(loginBody))
    }

    suspend fun register(body: RegisterBody):Observable<VortexResponse<AuthResponse>> {
        return provider.getObservable(getService().register(body))
    }

}