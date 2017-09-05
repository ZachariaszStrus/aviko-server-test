package com.company

import com.company.generator.Generator
import com.company.network.Http
import java.util.*

val host = "http://avikobenefits.pl/backend/api"
val signUp = host + "/account/register"


fun main(args: Array<String>) {

    /*val params = HashMap<String, String>()
    params.put("email", "s@ss.ss")
    val response = Http.post(signUp, params)

    System.out.println(response?.body().toString())*/

    val generator: Generator = Generator()
}
