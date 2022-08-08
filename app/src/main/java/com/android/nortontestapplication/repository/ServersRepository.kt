package com.android.nortontestapplication.repository

import com.android.nortontestapplication.database.AppDAO
import com.android.nortontestapplication.model.AuthToken
import com.android.nortontestapplication.model.LoginRequest
import com.android.nortontestapplication.model.ServersListItem
import com.android.nortontestapplication.network.RetrofitService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServersRepository @Inject constructor(private val api : RetrofitService ,
                                            private val dao: AppDAO) {

    suspend fun getToken(loginRequest: LoginRequest) = api.getToken(loginRequest)

    suspend fun getServersList(token: AuthToken) = api.getServersList(token)

    suspend fun insert(serversListItem: ServersListItem): Long{
       return dao.insertServers(serversListItem)
    }
}