package com.company

import com.company.generator.ImageGenerator
import com.company.model.PurchaseRegister
import com.company.model.UserLogin
import com.company.service.AccountService
import com.company.service.PurchaseService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.FileReader
import java.io.FileWriter
import kotlin.system.measureTimeMillis


object PurchaseWorker {
    private lateinit var userArray: ArrayList<UserLogin>
    private lateinit var purchaseArray: ArrayList<Int>
    lateinit var purchasesFileName: String
    private var timeSum = 0L


    fun start(usersFileName: String, purchasesFile: String) {
        readUsers(usersFileName)
        purchaseArray = ArrayList()
        purchasesFileName = purchasesFile

        for(user in userArray) {
            System.out.println("User : ${user.email}")
            val time = measureTimeMillis{
                registerPurchases(user)
            }
            timeSum += time
            System.out.println("User time : $time")
            System.out.println("--------------------------------------------------------------------")
        }

        System.out.println("--------------------------------------------------------------------")
        System.out.println("Average time : ${timeSum/userArray.size}")
        System.out.println("--------------------------------------------------------------------")

        savePurchases()
    }

    private fun registerPurchases(user: UserLogin) {
        var token = ""
        val time = measureTimeMillis{
            token = AccountService.signIn(user) ?: return
        }
        System.out.println("\tSign in time : $time")

        val purchaseService = PurchaseService(token)

        val purchasesNumber = ImageGenerator.getPurchasesNumber()
        for(i in 1..purchasesNumber) {
            val purchaseTime = measureTimeMillis{
                mageSinglePurchase(purchaseService)
            }
            System.out.println("\tPurchase $i time : $purchaseTime")
        }
    }

    private fun mageSinglePurchase(purchaseService: PurchaseService) {
        val photos = ImageGenerator.getPhotosList()
        val purchaseRegister = PurchaseRegister()

        photos[0].PP_Type = "0"
        purchaseRegister.purchasePictures.add(photos[0])

        photos[1].PP_Type = "A"
        purchaseRegister.purchasePictures.add(photos[1])

        for(i in 2..(photos.size - 1)) {
            photos[i].PP_Type = "Temp${i-1}"
            purchaseRegister.purchasePictures.add(photos[i])
        }

        val purchaseId = purchaseService.register(purchaseRegister)
        purchaseId?.let { this.purchaseArray.add(it) }
    }

    private fun readUsers(usersFileName: String) {
        val reader = JsonReader(FileReader(usersFileName))
        userArray = Gson().fromJson(reader, object : TypeToken<ArrayList<UserLogin>>() {}.type)
    }

    private fun savePurchases() {
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