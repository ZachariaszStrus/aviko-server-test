package com.company.generator

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
        var images = this.getList("images.json").images.filter { string -> string != "" }.toMutableList()
        images.removeAt(images.size - 6)

        val writer = JsonWriter(FileWriter("photos.json"))
        this.gson.toJson(
                images.map { img: String -> "data:image/jpeg;base64, $img" },
                object : TypeToken<List<String>>() {}.type,
                writer)

    }

    private fun getList(filename: String): Data {
        val reader = JsonReader(FileReader(filename))
        return this.gson.fromJson(reader, object : TypeToken<Data>() {}.type)
    }

    class Data(val images: List<String>)

}