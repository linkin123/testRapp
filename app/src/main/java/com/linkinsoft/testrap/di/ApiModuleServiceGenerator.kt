package com.linkinsoft.testrap.di

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.linkinsoft.testrap.BuildConfig
import com.linkinsoft.testrap.application.AppConstants
import com.linkinsoft.testrap.repository.WebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HttpsURLConnection

@Module
@InstallIn(SingletonComponent::class)
object ApiModuleServiceGenerator {

    @Singleton
    @Provides
    fun service() = retrofitEncrypt().create(WebService::class.java)

    fun retrofitEncrypt(): Retrofit {
        return retrofitBuilderEncrypt().client(httpBuilderEncrypt().build()).build()
    }

    fun retrofitBuilderEncrypt(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(getUnsafeOkHttpClient(httpBuilderEncrypt()))

    @SuppressLint("TrustAllX509TrustManager")
    private fun getUnsafeOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient {
        try {

            builder.hostnameVerifier { hostname, session ->
                if (!BuildConfig.DEBUG) {
                    val hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier()
                    return@hostnameVerifier hostnameVerifier.verify(hostname, session)
                }
                true
            }

            return builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun httpBuilderEncrypt(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(
                LoggingInterceptor.Builder()
                    .setLevel(Level.BASIC)
                    .log(Platform.INFO)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("version", BuildConfig.VERSION_NAME)
                    .request("Request")
                    .response("Response")
                    .log(Log.VERBOSE)
                    .build()
            )
    }



}