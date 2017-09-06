package com.company


fun main(args: Array<String>) {
    //SignUpWorker.start(6000)

    PurchaseWorker.start("users-test.json", "purchases.json")
//    PurchaseWorker.start("users2.json", "purchases2.json")
}


