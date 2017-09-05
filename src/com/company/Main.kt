package com.company

import com.company.model.UserLogin
import com.company.service.AccountService

import com.company.generator.ImageGenerator


fun main(args: Array<String>) {
    System.out.println(AccountService.signIn(UserLogin(
            "s@ss.ss",
            "q12345"
    )))
    //SignUpWorker.start(6000)

    ImageGenerator.init()

}


