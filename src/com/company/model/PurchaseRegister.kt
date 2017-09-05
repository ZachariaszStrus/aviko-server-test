package com.company.model


data class PurchaseRegister (
        val GPS_Lati: String = "TEST",
        val GPS_Long: String = "TEST",
        val purchasePictures: ArrayList<Photo> = ArrayList(),
        val purchaseQRcodes: ArrayList<String> = ArrayList()
)