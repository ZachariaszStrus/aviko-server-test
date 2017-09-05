package com.company

import com.company.model.UserLogin
import com.company.service.AccountService


fun main(args: Array<String>) {
    System.out.println(AccountService.signIn(UserLogin(
            "s@ss.ss",
            "q12345"
    )))
}


