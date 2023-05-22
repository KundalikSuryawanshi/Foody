package com.kundalik.foody.models

data class Recipe(

    val recipeId: String = "",
    val image: String,
    val name: String,
    val title: String,
    val timeToReady: String,
    val ingredients: String,
    val process: String,
    val category: String,
    val postDate: String,
    val postUserId: String,

    ) {

    constructor() : this(

        recipeId = "",
        image = "",
        name = "",
        title = "",
        timeToReady = "",
        ingredients = "",
        process = "",
        category = "",
        postDate = "",
        postUserId = ""
    )
}
