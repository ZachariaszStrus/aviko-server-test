package com.company

import com.company.generator.ImageGenerator
import com.company.model.Photo
import com.company.model.PurchaseRecord
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
    private var purchaseSum = 0
    private var saveFreq = 1


    fun start(usersFileName: String, purchasesFile: String, saveF: Int = 1) {
        readUsers(usersFileName)
        purchaseArray = ArrayList()
        purchasesFileName = purchasesFile
        purchaseSum = 0
        saveFreq = saveF

        var i = 1
        val size = userArray.size
        for(user in userArray) {
            try {
                System.out.println("User ($i/$size) : ${user.email}")
                val time = measureTimeMillis{
                    registerPurchases(user)
                }
                timeSum += time
                System.out.println("User time : $time")
                System.out.println("--------------------------------------------------------------------")
                i++


                if(i % saveFreq == 0) savePurchases()

                System.out.println("--------------------------------------------------------------------")
                System.out.println("Average time : ${timeSum/i}")
                System.out.println("Total purchases : ${purchaseSum}")
                System.out.println("--------------------------------------------------------------------")
            }
            catch (e: Exception) {
                System.out.println(e)
            }
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
        purchaseSum += purchasesNumber
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

        purchaseRegister.purchasePictures.add(
                Photo(purchaseService.savePhoto(photos[0])!!, "0")
        )

        purchaseRegister.purchasePictures.add(
                Photo(purchaseService.savePhoto(photos[1])!!, "A")
        )

        for(i in 2..(photos.size - 1)) {
            purchaseRegister.purchasePictures.add(
                    Photo(purchaseService.savePhoto(photos[i])!!, "Temp${i-1}")
            )
        }

        val purchaseId = purchaseService.register(purchaseRegister)
        purchaseId?.let { this.purchaseArray.add(it) }
    }

    private fun readUsers(usersFileName: String) {
        val reader = JsonReader(FileReader(usersFileName))
        userArray = Gson().fromJson(reader, object : TypeToken<ArrayList<UserLogin>>() {}.type)
    }

    private fun savePurchases() {
        System.out.println("Saving ... ")

        val reader = JsonReader(FileReader(purchasesFileName))
        val oldPurchaseArray: ArrayList<PurchaseRecord> =
                Gson().fromJson(reader, object : TypeToken<ArrayList<PurchaseRecord>>() {}.type)
        reader.close()

        val writer = JsonWriter(FileWriter(purchasesFileName))
        writer.setIndent("  ")
        writer.beginArray()

        for ((idPurchase, PurchaseItems, PurchaseStatus) in oldPurchaseArray) {
            writer.beginObject()
            writer.name("idPurchase").value(idPurchase)
            writer.name("PurchaseItems").value(PurchaseItems)
            writer.name("PurchaseStatus").value(PurchaseStatus)
            writer.endObject()
        }

        for (id in purchaseArray) {
            writer.beginObject()
            writer.name("idPurchase").value(id)
            writer.name("PurchaseItems").value(ImageGenerator.getProductsInPurchase())
            writer.name("PurchaseStatus").value(ImageGenerator.getPurchaseStatus())
            writer.endObject()
        }

        writer.endArray()
        writer.close()

        purchaseArray.clear()
    }
}