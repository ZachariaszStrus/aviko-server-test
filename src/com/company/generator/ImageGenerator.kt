package com.company.generator

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.io.FileReader

object ImageGenerator {
    private val gson = Gson()

    init {
        var counter = 0
        val data = this.getList("images.json");
        data.images.forEach(fun (img: String) {
            System.out.println(img.length)
            counter++
        })
        System.out.println(counter)
    }

    private fun getList(filename: String): Data {
        val reader = JsonReader(FileReader(filename))
        return gson.fromJson(reader, object : TypeToken<Data>() {}.type)
    }

    class Data(val images: List<String>)

}