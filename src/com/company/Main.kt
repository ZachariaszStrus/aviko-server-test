package com.company

import com.company.generator.Generator


fun main(args: Array<String>) {
    SignUpWorker.start(10)

    val generator: Generator
    generator.getUserRegister()

}


