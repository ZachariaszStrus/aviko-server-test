package com.company.network

import okhttp3.*

import java.io.IOException


object Http {
    fun post(targetURL: String, urlParameters: Map<String, String>): Response? {
        try {
            val client = OkHttpClient()

            val mediaType = MediaType.parse("application/json")
            val body = RequestBody.create(mediaType, urlParameters.toParamsString())

            val request = Request.Builder()
                    .url(targetURL)
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .build()

            val response = client.newCall(request).execute()
            return response
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }
}

fun Map<String, String>.toParamsString(): String {
    if(this.isEmpty()) return ""

    val result = StringBuilder()
    result.append("{")
    this.map {
        "\"${it.key}\":\"${ it.value}\","
    }.forEach {
        result.append(it)
    }
    result.deleteCharAt(result.length - 1)
    result.append("}")

    return result.toString()
}
