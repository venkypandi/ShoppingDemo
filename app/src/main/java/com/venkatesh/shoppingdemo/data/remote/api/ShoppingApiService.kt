package com.venkatesh.shoppingdemo.data.remote.api

import com.venkatesh.shoppingdemo.data.remote.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ShoppingApiService {

    @GET("/nancymadan/assignment/db")
    suspend fun getProductDetails():Response<ProductResponse>

}