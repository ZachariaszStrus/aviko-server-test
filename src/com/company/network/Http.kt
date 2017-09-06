package com.company.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*


val host = "http://eurekaweb.eu/backend/api"

object Http {
    private val client: OkHttpClient

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        client = OkHttpClient.Builder().addInterceptor(logging).build()
    }

    fun post(targetURL: String, urlParameters: Any, headers: Map<String, String> = HashMap()): Response? {

        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(mediaType, Gson().toJson(urlParameters))

        val requestBuilder = Request.Builder()
                .url(host + targetURL)
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")

        for((key, value) in headers) {
            requestBuilder.addHeader(key, value)
        }

        val request = requestBuilder.build()

        return client.newCall(request).execute()
    }
}

inline fun <reified T> Gson.fromJson2(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)