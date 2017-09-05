package com.company.generator

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.io.FileReader

class Generator {

    init {
        val gson = Gson()
        val reader = JsonReader(FileReader("names.json"))

        val dupa: List<Data> = gson.fromJson(reader, object : TypeToken<List<Data>>() {}.type)
    }

    class Data(val name: String, val popularity: Double)

}