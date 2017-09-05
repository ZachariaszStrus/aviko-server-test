package com.company.generator

import com.company.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.File
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

}