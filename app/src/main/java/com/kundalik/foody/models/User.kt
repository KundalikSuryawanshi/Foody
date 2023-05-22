package com.kundalik.foody.models

data class User(

    val userEmail: String,
    val firstName: String,
    val lastName: String,
    val userMobile: String,
    val address: String,
    val favCategory: String,

    ) {
    constructor() : this(

        userEmail = "",
        firstName = "",
        lastName = "",
        userMobile = "",
        address = "",
        favCategory = ""

    )

}
