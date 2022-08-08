package com.android.nortontestapplication.network

import android.content.Context
import com.android.nortontestapplication.utils.AuthInterceptor
import com.android.nortontestapplication.utils.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        private val BASE_URL: String = Constants.BASE_URL
        private val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        fun getRetrofitInstance(context: Context): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okhttpClient(context))
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
        }

        private fun okhttpClient(context: Context) : OkHttpClient{
            val client = OkHttpClient.Builder().apply {
                this.addInterceptor(interceptor)
                    .addInterceptor(AuthInterceptor(context))
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(25, TimeUnit.SECONDS)
            }.build()
            return client
        }
    }
}