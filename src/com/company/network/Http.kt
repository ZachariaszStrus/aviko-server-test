package com.company.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*


val host = "http://eurekaweb.eu/backend/api"

object Http {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun post(targetURL: String, urlParameters: Any): Response? {

        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(mediaType, gson.toJson(urlParameters))

        val request = Request.Builder()
                .url(host + targetURL)
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build()

        return client.newCall(request).execute()
    }
}

inline fun <reified T> Gson.fromJson2(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)