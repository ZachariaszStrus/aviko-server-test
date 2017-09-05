package com.company

import com.company.model.UserRegister
import com.company.service.AccountService
import com.google.gson.stream.JsonWriter
import java.io.FileWriter
import kotlin.system.measureTimeMillis


object SignUpWorker {

    /**
     * @return average http execution time
     */
    fun start(quantity: Int): Long {
        var timeSum = 0L
        for (i in 1..quantity) {
            System.out.print("$i) ")
            val time = measureTimeMillis{
                val token = AccountService.signUp(getUser(i))
                System.out.print("Token : $token, ")
            }
            timeSum += time
            System.out.print("Time : ${time}ms ")
            System.out.print("\n")
        }

        System.out.print("Average time : ${timeSum/quantity} \n")
        return timeSum/quantity
    }

    fun getUser(i: Int) = UserRegister("jan@wfdsfp.pl" + i, "Jan", "Kowalski")

    fun saveUsers(fileName: String, users: Collection<UserRegister>) {
        val writer = JsonWriter(FileWriter(fileName))

        writer.setIndent("  ")
        writer.beginArray()
        for (u in users) {
            writer.beginObject()
            writer.name("MemberEmail").value(u.MemberEmail)
            writer.name("password").value(u.password)
            writer.endObject()
        }
        writer.endArray()
        writer.close()
    }
}