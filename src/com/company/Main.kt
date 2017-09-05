package com.company

import com.company.network.Http
import java.util.*

val host = "http://avikobenefits.pl/backend/api"

fun main(args: Array<String>) {

    val params = HashMap<String, String>()
    params.put("email", "s@ss.ss")
    val response = Http.post(
            host + "/account/verify-email",
            params
    )

    System.out.println(response?.body().toString())
}
