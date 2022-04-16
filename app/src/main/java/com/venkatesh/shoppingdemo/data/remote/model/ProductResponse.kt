package com.venkatesh.shoppingdemo.data.remote.model


import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products")
    val products: List<Product>
)