package com.android.nortontestapplication.network

import com.android.nortontestapplication.model.AuthToken
import com.android.nortontestapplication.model.LoginRequest
import com.android.nortontestapplication.model.ServersListItem
import com.android.nortontestapplication.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    @POST(Constants.TOKEN_END_URL)
    suspend fun getToken(@Body request: LoginRequest) : Response<AuthToken>

    @GET(Constants.SERVER_END_URL)
    suspend fun getServersList(@Header(Constants.HEADER) token: AuthToken) : Response<List<ServersListItem>>

}