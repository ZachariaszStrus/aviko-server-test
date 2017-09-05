package com.company.service

import com.company.model.Photo
import com.company.model.PhotoSaveResponse
import com.company.model.PurchaseRegister
import com.company.model.PurchaseRegisterResponse
import com.company.network.Http
import com.company.network.fromJson2
import com.google.gson.Gson


class PurchaseService(val token: String) {
    val savePhoto = "/purchase/save-picture"
    val registerPurchase = "/purchase/register-purchase"


    fun savePhoto(photo: Photo): String? {
        val headers = HashMap<String, String>()
        headers.put("Authorization", "Bearer $token")

        val responseBody = Http.post(savePhoto, photo, headers)?.body()?.string().toString()
        val response = Gson().fromJson2<PhotoSaveResponse>(responseBody)

        return if(response.status == "ok") {
            response.PP_Photo
        }
        else {
            null
        }
    }

    fun register(purchase: PurchaseRegister): Int? {
        val headers = HashMap<String, String>()
        headers.put("Authorization", "Bearer $token")

        val responseBody = Http.post(registerPurchase, purchase, headers)?.body()?.string().toString()
        val response = Gson().fromJson2<PurchaseRegisterResponse>(responseBody)

        return if(response.status == "ok") {
            response.idPurchase
        }
        else {
            null
        }
    }
}