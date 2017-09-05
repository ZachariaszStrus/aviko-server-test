package com.company.generator

import com.google.gson.Gson
import java.io.FileReader
import com.google.gson.stream.JsonReader
import com.google.gson.reflect.TypeToken





class Generator {

    init {
        val gson = Gson()
        val reader = JsonReader(FileReader("names.json"))

        val dupa: List<Data> = gson.fromJson(reader, object : TypeToken<List<Data>>() {}.type)
    }

    class Data(val name: String, val popularity: Double)

}