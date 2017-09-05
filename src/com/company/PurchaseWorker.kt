package com.company

import com.company.model.UserLogin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.FileReader
import java.io.FileWriter


object PurchaseWorker {
    private lateinit var userArray: ArrayList<UserLogin>
    private lateinit var purchaseArray: ArrayList<Int>
    lateinit var purchasesFileName: String

    fun start(usersFileName: String, purchasesFile: String) {
        readUsers(usersFileName)
        purchaseArray = ArrayList()
        purchasesFileName = purchasesFile

        for(user in userArray) {

        }
    }

    fun readUsers(usersFileName: String) {
        val reader = JsonReader(FileReader(usersFileName))
        userArray = Gson().fromJson(reader, object : TypeToken<ArrayList<UserLogin>>() {}.type)
    }

    fun savePurchases() {
        val writer = JsonWriter(FileWriter(purchasesFileName))

        writer.setIndent("  ")
        writer.beginArray()

        for (id in purchaseArray) {
            writer.value(id)
        }

        writer.endArray()
        writer.close()
    }
}