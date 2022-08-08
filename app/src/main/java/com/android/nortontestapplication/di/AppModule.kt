package com.android.nortontestapplication.di

import android.content.Context
import com.android.nortontestapplication.database.AppDatabase
import com.android.nortontestapplication.database.AppDAO
import com.android.nortontestapplication.network.RetrofitInstance
import com.android.nortontestapplication.network.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun getAppDao(appDatabase: AppDatabase): AppDAO {
        return appDatabase.serverDAO
    }

    @Singleton
    @Provides
    fun getRetrofitServiceInstance(retrofit: Retrofit) : RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitInstance(@ApplicationContext context:Context) : Retrofit {
        return RetrofitInstance.getRetrofitInstance(context)
    }
}