package com.company.service

import com.company.model.LoginResponse
import com.company.model.RegisterResponse
import com.company.model.UserLogin
import com.company.model.UserRegister
import com.company.network.Http
import com.company.network.fromJson2
import com.google.gson.Gson

object AccountService {
    val signUp = "/account/register"
    val signIn = "/account/login"

    fun signUp(user: UserRegister): String? {
        val responseBody = Http.post(signUp, user)?.body()?.string().toString()
        val response = Gson().fromJson2<RegisterResponse>(responseBody)

        return if(response.status == "ok") {
            response.data["token"]
        }
        else {
            null
        }
    }

    fun signIn(user: UserLogin): String? {
        val responseBody = Http.post(signIn, user)?.body()?.string().toString()
        val response = Gson().fromJson2<LoginResponse>(responseBody)

        return if(response.status == "ok") {
            response.token
        }
        else {
            null
        }
    }
}