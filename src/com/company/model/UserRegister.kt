package com.company.model


data class UserRegister (
        var MemberEmail: String,
        var MemberName: String,
        var MemberSurname: String,
        var password: String,
        var MemberMarketingPermission: Int = 1,
        var CheckRegulations: Int = 1,
        var CheckData: Int = 1,
        var MemberSex: Int = Math.round(Math.random()).toInt()
)