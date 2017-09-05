package com.company.service

import com.company.model.Photo
import com.company.model.PhotoSaveResponse
import com.company.model.PurchaseRegister
import com.company.model.PurchaseRegisterResponse
import com.company.network.Http
import com.company.network.fromJson2
import com.google.gson.Gson


object PurchaseService {
    val savePhoto = "/purchase/register-purchase"
    val registerPurchase = "/purchase/register-purchase"

    fun savePhoto(photo: Photo): String? {
        val responseBody = Http.post(savePhoto, photo)?.body()?.string().toString()
        val response = Gson().fromJson2<PhotoSaveResponse>(responseBody)

        return if(response.status == "ok") {
            response.PP_Photo
        }
        else {
            null
        }
    }

    fun register(purchase: PurchaseRegister): Int? {
        val responseBody = Http.post(registerPurchase, purchase)?.body()?.string().toString()
        val response = Gson().fromJson2<PurchaseRegisterResponse>(responseBody)

        return if(response.status == "ok") {
            response.idPurchase
        }
        else {
            null
        }
    }
}