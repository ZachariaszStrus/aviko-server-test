package com.company.generator

import com.google.gson.Gson
import java.io.FileReader
import com.google.gson.stream.JsonReader
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.HashMap

class Generator {
    private val gson = Gson()
    private val names = this.getList("names.json")
    private val surnames = this.getList("surnames.json")
    private val random = Random()

    private val emails = HashMap<String, Int>()

    init {
        for (i in 1..100000) {
            System.out.println(this.getUserRegister())
        }
    }

    fun getUserRegister(): String {
        val name = this.randomEntry(this.names).name
        val surname = this.randomEntry(this.surnames).name
        val login = this.generateLogin(name, surname)

        return if(!this.emails.contains(login)) {
            this.emails.put(login, 0)
            this.generateEmail(login)
        } else {
            val value = this.emails[login]!!.inc()
            this.emails.put(login, value)
            this.generateEmail(login + value)
        }

    }

    private fun generateLogin(name: String, surname: String): String {
        return this.removeSpecialCharacters(name) + '.' + this.removeSpecialCharacters(surname)
    }

    private fun removeSpecialCharacters(string: String): String {
        val list = string.toLowerCase().toCharArray()
        list.forEachIndexed { index, char ->
            when(char) {
                'ó' -> list[index] = 'o'
                'ą' -> list[index] = 'a'
                'ć' -> list[index] = 'c'
                'ę' -> list[index] = 'e'
                'ł' -> list[index] = 'l'
                'ń' -> list[index] = 'n'
                'ś' -> list[index] = 's'
                'ź' -> list[index] = 'z'
                'ż' -> list[index] = 'z'
            }
        }
        return String(list)
    }

    private fun generateEmail(login: String): String {
        return "$login@gmail${this.random.nextInt(1000000) + 1000000}.com"
    }

    private fun randomEntry(list: List<Data>): Data {
        val len = this.names.size
        var max = 0
        this.names.forEach(fun(entry: Data) {
            val popularity = (entry.popularity * 1000).toInt()
            if(popularity > max)
                max = popularity
        })

        while(true) {
            val index: Int = this.random.nextInt(len)
            val value: Int = this.random.nextInt(max)

            if(value < list[index].popularity)
                return list[index]
        }
    }

    private fun getList(filename: String): List<Data> {
        val reader = JsonReader(FileReader(filename))
        return gson.fromJson(reader, object : TypeToken<List<Data>>() {}.type)
    }

    class Data(val name: String, val popularity: Double)

}
