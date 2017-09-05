package com.company.service

import com.company.model.*
import com.company.network.Http
import com.company.network.fromJson2
import com.google.gson.Gson

object AccountService {
    val signUp = "/account/register"
    val signIn = "/account/login"
    val authorizeAccounts = "/test/authorize-users"

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

    fun authorizeAccounts(date: String): Boolean {
        val headers = HashMap<String, String>()
        headers.put("Authorization", "Basic YXBpQHRlc3Q6NWc2NE84a3NUOXhUcUd2")

        val responseBody = Http.post(
                authorizeAccounts, date, headers
        )?.body()?.string().toString()

        val response = Gson().fromJson2<Statusable>(responseBody)

        return response.status == "ok"
    }
}