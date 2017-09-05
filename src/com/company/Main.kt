package com.company

import com.company.model.UserRegister
import com.company.service.AccountService

fun main(args: Array<String>) {

    val user = UserRegister(
            "jan3323s13@wp.pl",
            "Jan",
            "Kowalski")

    val response = AccountService.signUp(user)
    System.out.println(response)
}
