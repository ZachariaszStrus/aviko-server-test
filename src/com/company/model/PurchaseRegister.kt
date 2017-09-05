package com.company.model


data class PurchaseRegister (
        val GPS_Lati: String = "TEST",
        val GPS_Long: String = "TEST",
        val purchasePictures: ArrayList<String> = ArrayList(),
        val purchaseQRcodes: ArrayList<String> = ArrayList()

)


//        "GPS_Lati": "XX.XXXXXX",
//"GPS_Long": "XX.XXXXXX",
//"purchasePictures": [
//{
//    "PP_Photo": "http://lorempixel.com/400/200/",
//    "PP_Type": "0"
//}
//]
//"purchaseQRcodes": []