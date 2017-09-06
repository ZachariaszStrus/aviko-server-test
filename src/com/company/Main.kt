package com.company

import com.company.generator.ImageGenerator
import com.company.service.AccountService


fun main(args: Array<String>) {
    //SignUpWorker.start(6000)

    //PurchaseWorker.start("users2.json", "purchases.json")

    ImageGenerator.init()
//    PurchaseWorker.start("users2.json", "purchases.json")

    val s = AccountService.authorizeAccounts("2017-09-05 12:00:00")
    System.out.println(s)
}


