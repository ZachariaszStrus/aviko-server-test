package com.company

import com.company.generator.Generator
import com.company.model.UserLogin
import com.company.service.AccountService
import com.google.gson.stream.JsonWriter
import java.io.FileWriter
import kotlin.system.measureTimeMillis


object SignUpWorker {
    var timeSum = 0L
    var signUpFailures = 0
    val userArray = ArrayList<UserLogin>()

    fun start(quantity: Int) {
        userArray.ensureCapacity(quantity)
        timeSum = 0L
        signUpFailures = 0
        for (i in 1..quantity) {
            val user = signUpSingleUser()
            if(user != null) {
                userArray.add(user)
            }
        }

        saveUsers("users.json", userArray)

        System.out.print("Average time : ${timeSum/quantity}ms \n")
        System.out.print("Total failures : ${signUpFailures}ms \n")
    }

    private fun signUpSingleUser(): UserLogin? {
        val user = getUser()
        var token: String? = null

        val time = measureTimeMillis{
            token = AccountService.signUp(user)
            if(token == null) {
                signUpFailures++
                System.out.print("Error - ${user.MemberEmail}")
            }
            else {
                System.out.print("User : ${user.MemberEmail} - ${user.MemberName} - ${user.MemberSurname}, ")
            }
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

    private fun getUser() = Generator.getUserRegister()

    private fun saveUsers(fileName: String, users: ArrayList<UserLogin>) {
        val writer = JsonWriter(FileWriter(fileName))

        writer.setIndent("  ")
        writer.beginArray()
        for (u in users) {
            writer.beginObject()
            writer.name("MemberEmail").value(u.email)
            writer.name("password").value(u.password)
            writer.endObject()
        }
        writer.endArray()
        writer.close()
    }
}