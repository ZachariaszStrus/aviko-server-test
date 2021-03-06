package com.company

import com.company.generator.UserGenerator
import com.company.model.UserLogin
import com.company.service.AccountService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import kotlin.system.measureTimeMillis


object SignUpWorker {
    private var timeSum = 0L
    private var signUpFailures = 0
    private var userArray = ArrayList<UserLogin>()

    fun start(quantity: Int, fileName: String = "users_1.json") {
        userArray = ArrayList(quantity)
        timeSum = 0L
        signUpFailures = 0
        for (i in 1..quantity) {
            val user = signUpSingleUser()
            if(user != null) {
                userArray.add(user)
                saveUsers(fileName, userArray)
            }
        }

        System.out.print("Average time : ${timeSum/quantity}ms \n")
        System.out.print("Total failures : ${signUpFailures} \n")
    }

    private fun signUpSingleUser(): UserLogin? {
        val user = getUser()
        var token: String? = null

        // measure time of http request
        val time = measureTimeMillis{
            token = AccountService.signUp(user)
        }

        if(token == null) {
            signUpFailures++
            System.out.print("Error : ${user.MemberEmail}, \t\t")
        }
        else {
            System.out.print("Success : ${user.MemberEmail}, \t\t")
        }

        timeSum += time
        System.out.print("Time : ${time}ms ")
        System.out.print("\n")

        return if(token != null) {
            UserLogin(user.MemberEmail, user.password)
        }
        else {
            null
        }
    }

    private fun getUser() = UserGenerator.getUserRegister()

    private fun saveUsers(fileName: String, users: ArrayList<UserLogin>) {
        System.out.println("Saving ... ")

        var oldArray: ArrayList<UserLogin>? = null
        try {
            val reader = JsonReader(FileReader(fileName))
            oldArray =
                    Gson().fromJson(reader, object : TypeToken<ArrayList<UserLogin>>() {}.type)
            reader.close()

        }
        catch (e: FileNotFoundException) { }



        val writer = JsonWriter(FileWriter(fileName))

        writer.setIndent("  ")
        writer.beginArray()

        if(oldArray != null) {
            for ((email, password) in oldArray) {
                writer.beginObject()
                writer.name("email").value(email)
                writer.name("password").value(password)
                writer.endObject()
            }
        }

        for ((email, password) in users) {
            writer.beginObject()
            writer.name("email").value(email)
            writer.name("password").value(password)
            writer.endObject()
        }

        writer.endArray()
        writer.close()
        users.clear()
    }
}