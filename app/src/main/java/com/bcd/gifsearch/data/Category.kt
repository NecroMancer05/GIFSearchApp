package com.bcd.gifsearch.data

import com.google.gson.annotations.SerializedName

data class Category(
    val name: String,
    @SerializedName("searchterm") val searchTerm: String,
    val path: String,
    val image: String,
)
