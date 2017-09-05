package com.company.service

import com.company.model.RegisterResponse
import com.company.model.UserRegister
import com.company.network.Http
import com.company.network.fromJson2
import com.google.gson.Gson

val signUp = "/account/register"

object AccountService {
    private val gson = Gson()

    fun signUp(user: UserRegister): String? {
        val responseBody = Http.post(signUp, user)?.body()?.string().toString()
        val response = gson.fromJson2<RegisterResponse>(responseBody)

        return if(response.status == "ok") {
            response.data["token"]
        }
        else {
            null
        }
    }
}