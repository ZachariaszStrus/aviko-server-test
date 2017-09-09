package com.company

import com.company.generator.ImageGenerator
import com.company.model.PurchaseRecord
import com.company.service.TestService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.FileReader
import java.io.FileWriter
import java.net.SocketTimeoutException


fun main(args: Array<String>) {
    //SignUpWorker.start(6000)

//    PurchaseWorker.start("users_zaki_2.json", "purchases_zaki_2.json")
//    PurchaseWorker.start("users2.json", "purchases2.json")


//    savePurchases("purchases_all.json", TestService.getList(
//            "2017-09-05 12:00:00",
//            "2017-09-07 12:00:00"
//    ))


//    verifyPurchases()

    PurchaseWorker.start("users_2_1.json", "purchases_2_1.json")
//    AccountService.authorizeAccounts("2017-09-07 23:55:00")
}

fun verifyPurchases() {
//    savePurchases("purchases_all.json", TestService.getList(
//            "2017-09-05 12:00:00",
//            "2017-09-07 12:00:00"
//    ))

    val purchases = readPurchases("purchases_all.json")


    val indent = 25
    for (i in 100..(purchases.size/indent)) {
        try {
            System.out.println("${i*indent}, purchase: ${purchases[i*indent]}")
            val arr = purchases.slice(IntRange(i*indent, (i+1)*indent)) as ArrayList<PurchaseRecord>
            TestService.verifyPurchases(
                    arr
            )
        }
        catch (e: SocketTimeoutException) {
            System.out.println(e)
        }
    }
}

private fun savePurchases(purchasesFileName: String, purchaseArray: ArrayList<PurchaseRecord>) {
    val writer = JsonWriter(FileWriter(purchasesFileName))
    writer.setIndent("  ")
    writer.beginArray()

    for ((idPurchase) in purchaseArray) {
        writer.beginObject()
        writer.name("idPurchase").value(idPurchase)
        writer.name("PurchaseItems").value(ImageGenerator.getProductsInPurchase())
        writer.name("PurchaseStatus").value(ImageGenerator.getPurchaseStatus())
        writer.endObject()
    }

    writer.endArray()
    writer.close()
}

private fun readPurchases(purchasesFileName: String): ArrayList<PurchaseRecord> {
    val reader = JsonReader(FileReader(purchasesFileName))
    val purchaseArray: ArrayList<PurchaseRecord> =
            Gson().fromJson(reader, object : TypeToken<ArrayList<PurchaseRecord>>() {}.type)
    reader.close()

    return purchaseArray
}


