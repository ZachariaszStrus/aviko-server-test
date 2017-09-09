package com.company.service

import com.company.model.PurchaseRecord
import com.company.model.Statusable
import com.company.network.Http
import com.company.network.fromJson2
import com.google.gson.Gson


object TestService {
    val verifyPurchase = "/test/verify-purchase"
    val getPurchases = "/test/get-purchases-id"

    fun verifyPurchases(Purchases: ArrayList<PurchaseRecord>): Boolean {
        val headers = HashMap<String, String>()
        headers.put("Authorization", "Basic YXBpQHRlc3Q6NWc2NE84a3NUOXhUcUd2")

        val responseBody = Http.post(
                verifyPurchase, VerifyPurchasesRequest(Purchases), headers
        )?.body()?.string().toString()

        val response = Gson().fromJson2<Statusable>(responseBody)

        return response.status == "ok"
    }

    fun getList(
            PurchaseRegisteredFrom: String,
            PurchaseRegisteredTo: String
    ): ArrayList<PurchaseRecord> {
        val headers = HashMap<String, String>()
        headers.put("Authorization", "Basic YXBpQHRlc3Q6NWc2NE84a3NUOXhUcUd2")

        val map = mapOf(
                Pair("PurchaseRegisteredFrom", PurchaseRegisteredFrom),
                Pair("PurchaseRegisteredTo", PurchaseRegisteredTo)
        )

        val responseBody = Http.post(
                getPurchases, map, headers
        )?.body()?.string().toString()

        val response = Gson().fromJson2<PurchasesListResponse>(responseBody)

        return if(response.status == "ok") {
            response.Purchases
        } else {
            arrayListOf()
        }
    }
}

data class VerifyPurchasesRequest(
        val Purchases: ArrayList<PurchaseRecord>
)

data class PurchasesListResponse(
        val status: String,
        val Purchases: ArrayList<PurchaseRecord>
)