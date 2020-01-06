package com.culttrip.data

import com.culttrip.data.models.VortexResponse
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.vortex.android.data.VortexRepository
import io.vortex.android.models.VortexRequestDetailsProvider
import io.vortex.android.models.VortexServiceProviderType
import io.vortex.android.rx.VortexRequestProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created By : Yazan Tarifi
 * Date : 1/5/2020
 * Time : 1:40 PM
 */

abstract class CulttripRepository<Api> : VortexRepository<Api>() {

    override fun getBaseUrl(): String {
        return "http://192.168.1.2:8181/v1/"
    }

    override fun getRequestDetails(): VortexRequestDetailsProvider {
       return VortexRequestDetailsProvider()
    }

    fun getLoggerReactiveRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getRetrofitLoggingInterceptor())
            .build()
    }

    private fun getRetrofitLoggingInterceptor(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)
        return httpClient.build()
    }

}
