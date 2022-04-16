package com.venkatesh.shoppingdemo.data.remote.model


import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("rating")
    val rating: Int
)