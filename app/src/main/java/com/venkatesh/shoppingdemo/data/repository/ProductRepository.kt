package com.venkatesh.shoppingdemo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.venkatesh.shoppingdemo.data.remote.api.ShoppingApiService
import com.venkatesh.shoppingdemo.data.remote.model.ProductResponse
import com.venkatesh.shoppingdemo.utils.Resource
import javax.inject.Inject


class ProductRepository @Inject constructor(private val shoppingService:ShoppingApiService) {

    private var _productResponse = MutableLiveData<Resource<ProductResponse>>()
    val productResponse:LiveData<Resource<ProductResponse>> = _productResponse

    suspend fun getProductDetails() {
        _productResponse.value = Resource.loading(null)
        val response = shoppingService.getProductDetails()
        if (response.isSuccessful) {
            _productResponse.value = Resource.success(response.body())
        } else {
            _productResponse.value = Resource.error("No products found",null)
        }
    }
}

