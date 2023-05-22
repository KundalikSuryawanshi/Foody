package com.kundalik.foody.models

data class Abuse(

    val report: String = "",
    val informer: String="",
    val abuseRecipeCreator: String="",
    val abuseRecipe: String = ""

) {
    constructor() : this (
        report = "",
        informer = "",
        abuseRecipeCreator = "",
        abuseRecipe = ""
    )
}
