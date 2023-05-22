package com.kundalik.foody.utils

import java.util.*

class GenerateKeys {

    fun generateRandomPassword(length: Int): String {
        val allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray()
        val random = Random()
        val password = CharArray(length)

        for (i in 0 until length) {
            val randomIndex = random.nextInt(allowedChars.size)
            password[i] = allowedChars[randomIndex]
        }
        return String(password)
    }
}