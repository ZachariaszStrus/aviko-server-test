package com.company.generator

import com.company.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.FileReader
import java.io.FileWriter

object ImageGenerator {
    private val gson = Gson()

    fun init() {
        val photos = this.getList("photos.json")
    }

    private fun getList(filename: String): List<Photo> {
        val reader = JsonReader(FileReader(filename))
        return this.gson.fromJson(reader, object : TypeToken<List<Photo>>() {}.type)
    }


    fun getPhotosList(): ArrayList<Photo> {
        val result = ArrayList<Photo>()
        result.add(Photo("data:image/jpeg;base64, null"))
        result.add(Photo("data:image/jpeg;base64, null1"))
        result.add(Photo("data:image/jpeg;base64, null2"))

        return result
    }

    fun getPurchasesNumber(): Int {
        return 2
    }

    fun getProductsInPurchase(): Int {
        return 2
    }

    fun getPurchaseStatus(): Int {
        return 1 // 1/0
    }
}